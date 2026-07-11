package com.duoc.plataformatransportes.servicio.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.plataformatransportes.dto.CrearGuiaRequest;
import com.duoc.plataformatransportes.dto.GuiaResponse;
import com.duoc.plataformatransportes.dto.SubirGuiaRequest;
import com.duoc.plataformatransportes.mapeador.GuiaMapper;
import com.duoc.plataformatransportes.modelo.Asset;
import com.duoc.plataformatransportes.modelo.GuiaDespacho;
import com.duoc.plataformatransportes.servicio.EfsService;
import com.duoc.plataformatransportes.servicio.GuiaService;
import com.duoc.plataformatransportes.servicio.PdfService;
import com.duoc.plataformatransportes.servicio.RabbitProducer;
import com.duoc.plataformatransportes.servicio.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuiaServiceImpl
        implements GuiaService {

    private final PdfService pdfService;

    private final S3Service s3Service;

    private final EfsService efsService;

    private final GuiaMapper mapper;

    private final RabbitProducer rabbitProducer;

    @Override
    public GuiaResponse crearGuia(
            CrearGuiaRequest request
    ) {

        GuiaDespacho guia =
                mapper.toModel(
                        request
                );

        File pdf =
                pdfService.generarPdf(
                        guia
                );

        Asset efsAsset =
                efsService.guardarTemporal(
                        pdf
                );

        Asset s3Asset =
                s3Service.subirArchivo(
                        construirKey(
                                guia
                        ),
                        pdf
                );

        System.out.println("=== ENVIANDO A RABBIT ===");

        String resumen = """
                        {
                        "numeroGuia": "%s",
                        "transportista": "%s",
                        "fecha": "%s",
                        "descripcion": "%s"
                        }
                        """.formatted(
                        guia.getNumeroGuia(),
                        guia.getTransportista(),
                        guia.getFecha(),
                        guia.getDescripcion()
                        );

        rabbitProducer.enviarResumen(resumen);

        System.out.println("=== MENSAJE ENVIADO ===");

        return mapper.toResponse(
                guia,
                s3Asset
        );

    }

    @Override
    public GuiaResponse subirGuia(
            SubirGuiaRequest request
    ) {

        try {

            File archivo =
                    multipartToFile(
                            request.getArchivo()
                    );

            GuiaDespacho guia =
                    mapper.toModel(
                            request
                    );

            efsService.guardarTemporal(
                    archivo
            );

            Asset s3Asset =
                    s3Service.subirArchivo(
                            construirKey(
                                    guia
                            ),
                            archivo
                    );

            return mapper.toResponse(
                    guia,
                    s3Asset
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    "Error al subir guía",
                    e
            );

        }

    }

    @Override
    public byte[] descargarGuia(
            String key
    ) {

        return s3Service
                .descargarArchivo(
                        key
                );

    }

    @Override
    public GuiaResponse actualizarGuia(
            String key,
            SubirGuiaRequest request
    ) {

        try {

            File archivo =
                    multipartToFile(
                            request.getArchivo()
                    );

            GuiaDespacho guia =
                    mapper.toModel(
                            request
                    );

            Asset asset =
                    s3Service
                            .actualizarArchivo(
                                    key,
                                    archivo
                            );

            return mapper.toResponse(
                    guia,
                    asset
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    "Error actualizando guía",
                    e
            );

        }

    }

    @Override
    public void eliminarGuia(
            String key
    ) {

        s3Service
                .eliminarArchivo(
                        key
                );

    }

    private String construirKey(
            GuiaDespacho guia
    ) {

        String transportista =
                guia.getTransportista()
                        .trim()
                        .replace(
                                " ",
                                "-"
                        );

        return String.format(
        "%d%02d/%s/%s.pdf",

                guia.getFecha()
                        .getYear(),

                guia.getFecha()
                        .getMonthValue(),

                transportista,

                guia.getNumeroGuia()
        );

    }

    private File multipartToFile(
            MultipartFile multipart
    )
            throws IOException {

        File archivo =
                File.createTempFile(
                        "guia-",
                        ".pdf"
                );

        multipart.transferTo(
                archivo
        );

        return archivo;

    }

    @Override
    public List<Asset> consultarGuias(
                Integer anio,
                String transportista
     ) {

        return s3Service
                .consultarGuias(
                        anio,
                        transportista
                );

    }

}
