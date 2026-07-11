package com.duoc.plataformatransportes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuiaResponse {

    String numeroGuia;

    String nombreArchivo;

    String transportista;

    String s3Key;

    String url;

}
