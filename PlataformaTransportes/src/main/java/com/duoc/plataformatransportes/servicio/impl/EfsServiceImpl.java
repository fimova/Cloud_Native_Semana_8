package com.duoc.plataformatransportes.servicio.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.duoc.plataformatransportes.modelo.Asset;
import com.duoc.plataformatransportes.servicio.EfsService;

@Service
public class EfsServiceImpl
implements EfsService {

    @Value("${aws.efs.path}")
    private String efsPath;

    @Override
    public Asset guardarTemporal(File archivo) {
        try {

                Path carpeta =
                        Path.of(
                                efsPath
                        );

                Files.createDirectories(
                        carpeta
                );

                Path destino =
                        carpeta.resolve(
                                archivo.getName()
                        );

                Files.copy(
                        archivo.toPath(),
                        destino
                );

                return Asset.builder()
                        .name(
                                archivo.getName()
                        )
                        .key(
                                destino.toString()
                        )
                        .build();

        } catch (
                IOException e
        ) {

                throw new RuntimeException(
                        e
                );

        }

    }

    @Override
    public byte[] obtenerArchivo(
            String ruta
    ) {

        try {

            return Files.readAllBytes(
                    Path.of(
                            ruta
                    )
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    e
            );

        }

    }

    @Override
    public void eliminarTemporal(
            String ruta
    ) {

        try {

            Files.deleteIfExists(
                    Path.of(
                            ruta
                    )
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    e
            );

        }

    }

}
