package com.duoc.consumidor.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.consumidor.entidad.ResumenGuia;
import com.duoc.consumidor.repositorio.ResumenGuiaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResumenController {

    private final ResumenGuiaRepository repository;

    @GetMapping("/resumen/ultimo")
    public ResponseEntity<ResumenGuia> obtenerUltimoResumen() {

        return repository.findTopByOrderByIdDesc()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }

}
