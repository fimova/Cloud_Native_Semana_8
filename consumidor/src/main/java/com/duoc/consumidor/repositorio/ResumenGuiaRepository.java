package com.duoc.consumidor.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.consumidor.entidad.ResumenGuia;

public interface ResumenGuiaRepository extends JpaRepository<ResumenGuia, Long> {

    Optional<ResumenGuia> findTopByOrderByIdDesc();

}

