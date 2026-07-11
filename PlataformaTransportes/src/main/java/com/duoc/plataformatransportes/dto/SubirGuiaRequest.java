package com.duoc.plataformatransportes.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubirGuiaRequest {

    @NotNull(message ="Debe adjuntar un archivo")
    MultipartFile archivo;

    @NotBlank(message ="El número de guía es obligatorio")
    String numeroGuia;

    @NotBlank( message = "El transportista es obligatorio")
    @Size(max = 50)
    String transportista;

    @NotNull(message = "La fecha es obligatoria")
    LocalDate fecha;

}