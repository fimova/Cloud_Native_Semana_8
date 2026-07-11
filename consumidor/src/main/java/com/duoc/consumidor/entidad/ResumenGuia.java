package com.duoc.consumidor.entidad;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumen_inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenGuia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String resumen;

}