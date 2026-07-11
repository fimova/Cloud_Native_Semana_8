package com.duoc.plataformatransportes.controlador;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.plataformatransportes.dto.CrearGuiaRequest;
import com.duoc.plataformatransportes.dto.GuiaResponse;
import com.duoc.plataformatransportes.dto.SubirGuiaRequest;

import com.duoc.plataformatransportes.modelo.Asset;

import com.duoc.plataformatransportes.servicio.GuiaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/guias")
@RequiredArgsConstructor
@Validated
public class GuiaController {

    private final GuiaService guiaService;

    @PostMapping
    public GuiaResponse crearGuia(
            @Valid
            @RequestBody
            CrearGuiaRequest request
    ) {

        return guiaService
                .crearGuia(
                        request
                );

    }

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
        )
        public GuiaResponse subirGuia(

                @RequestPart("archivo")
                MultipartFile archivo,

                @RequestParam
                String numeroGuia,

                @RequestParam
                String transportista,

                @RequestParam
                String fecha,

                @RequestParam
                String descripcion
        ) {

        SubirGuiaRequest request =
                SubirGuiaRequest
                        .builder()
                        .archivo(
                                archivo
                        )
                        .numeroGuia(
                                numeroGuia
                        )
                        .transportista(
                                transportista
                        )
                        .fecha(
                                LocalDate.parse(
                                        fecha
                                )
                        )
                        .build();

        return guiaService.subirGuia(
                request
        );

        }

    @GetMapping(
            "/download"
    )
    public ResponseEntity<ByteArrayResource>
    descargarGuia(
            @RequestParam
            String key
    ) {

        byte[] archivo =
                guiaService
                        .descargarGuia(
                                key
                        );

        ByteArrayResource recurso =
                new ByteArrayResource(
                        archivo
                );

        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=guia.pdf"
                )
                .body(
                        recurso
                );

    }

   @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GuiaResponse actualizarGuia(

                @RequestParam
                String key,

                @ModelAttribute
                @Valid
                SubirGuiaRequest request
        ) {

        return guiaService
                .actualizarGuia(
                        key,
                        request
                );

        }

    @DeleteMapping
    public ResponseEntity<Void> eliminarGuia(
                @RequestParam
                String key
        ) {

        guiaService.eliminarGuia(
                key
        );

        return ResponseEntity
                .noContent()
                .build();

        }

    @GetMapping
    public List<Asset> consultarGuias(
            @RequestParam
            Integer anio,

            @RequestParam
            String transportista
    ) {

        return guiaService
                .consultarGuias(
                        anio,
                        transportista
                );

    }

}
