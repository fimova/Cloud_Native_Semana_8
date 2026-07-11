package com.duoc.plataformatransportes.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespacho {

    String numeroGuia;

    String transportista;

    LocalDate fecha;

    String descripcion;

}