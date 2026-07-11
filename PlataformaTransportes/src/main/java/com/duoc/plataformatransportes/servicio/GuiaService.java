package com.duoc.plataformatransportes.servicio;

import java.util.List;

import com.duoc.plataformatransportes.dto.CrearGuiaRequest;
import com.duoc.plataformatransportes.dto.GuiaResponse;
import com.duoc.plataformatransportes.dto.SubirGuiaRequest;
import com.duoc.plataformatransportes.modelo.Asset;

public interface GuiaService {

    GuiaResponse crearGuia(
            CrearGuiaRequest request
    );

    GuiaResponse subirGuia(
            SubirGuiaRequest request
    );

    byte[] descargarGuia(
            String key
    );

    GuiaResponse actualizarGuia(
            String key,
            SubirGuiaRequest request
    );

    void eliminarGuia(
            String key
    );

    List<Asset> consultarGuias(
        Integer anio,
        String transportista
    );

}