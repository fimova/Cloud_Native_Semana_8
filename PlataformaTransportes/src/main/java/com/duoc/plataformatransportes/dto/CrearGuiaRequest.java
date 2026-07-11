package com.duoc.plataformatransportes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearGuiaRequest {

    @NotBlank(
        message =
        "El número de guía es obligatorio"
    )
    private String numeroGuia;

    @NotBlank(
        message =
        "El transportista es obligatorio"
    )
    @Size(max = 50)
    private String transportista;

    @NotNull(
        message =
        "La fecha es obligatoria"
    )
    private LocalDate fecha;

    @NotBlank(
        message =
        "La descripción es obligatoria"
    )
    @Size(max = 500)
    private String descripcion;

}
