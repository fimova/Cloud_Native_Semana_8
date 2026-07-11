package com.duoc.plataformatransportes.servicio;

import java.io.File;
import java.util.List;

import com.duoc.plataformatransportes.modelo.Asset;

public interface S3Service {

    Asset subirArchivo(
            String key,
            File archivo
    );

    byte[] descargarArchivo(
            String key
    );

    void eliminarArchivo(
            String key
    );

    Asset actualizarArchivo(
            String key,
            File archivo
    );

    List<Asset> consultarGuias(
            Integer anio,
            String transportista
    );

}