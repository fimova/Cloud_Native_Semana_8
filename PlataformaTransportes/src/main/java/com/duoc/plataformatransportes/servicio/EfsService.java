package com.duoc.plataformatransportes.servicio;

import java.io.File;

import com.duoc.plataformatransportes.modelo.Asset;

public interface EfsService {

    Asset guardarTemporal(
            File archivo
    );

    byte[] obtenerArchivo(
            String ruta
    );

    void eliminarTemporal(
            String ruta
    );

}