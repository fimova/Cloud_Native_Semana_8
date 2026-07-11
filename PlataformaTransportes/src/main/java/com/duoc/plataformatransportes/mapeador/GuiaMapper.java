package com.duoc.plataformatransportes.mapeador;

import org.springframework.stereotype.Component;

import com.duoc.plataformatransportes.dto.CrearGuiaRequest;
import com.duoc.plataformatransportes.dto.GuiaResponse;
import com.duoc.plataformatransportes.dto.SubirGuiaRequest;
import com.duoc.plataformatransportes.modelo.Asset;
import com.duoc.plataformatransportes.modelo.GuiaDespacho;

import java.time.LocalDate;

@Component
public class GuiaMapper {

    public GuiaDespacho toModel(
            CrearGuiaRequest request
    ) {

        return GuiaDespacho.builder()
                .numeroGuia(
                        request.getNumeroGuia()
                )
                .transportista(
                        request.getTransportista()
                )
                .fecha(
                        request.getFecha()
                )
                .descripcion(
                        request.getDescripcion()
                )
                .build();

    }

    public GuiaDespacho toModel(
            SubirGuiaRequest request
    ) {

        return GuiaDespacho.builder()
                .numeroGuia(
                        request.getNumeroGuia()
                )
                .transportista(
                        request.getTransportista()
                )
                .fecha(
                        request.getFecha()
                )
                .build();

    }

    public GuiaResponse toResponse(
            GuiaDespacho guia,
            Asset asset
    ) {

        return GuiaResponse.builder()
                .numeroGuia(
                        guia.getNumeroGuia()
                )
                .nombreArchivo(
                        asset.getName()
                )
                .transportista(
                        guia.getTransportista()
                )
                .s3Key(
                        asset.getKey()
                )
                .url(
                        asset.getUrl() != null
                                ? asset.getUrl()
                                        .toString()
                                : null
                )
                .build();

    }

}
