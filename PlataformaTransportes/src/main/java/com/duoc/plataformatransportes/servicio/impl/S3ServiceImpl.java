package com.duoc.plataformatransportes.servicio.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.duoc.plataformatransportes.modelo.Asset;
import com.duoc.plataformatransportes.repositorio.S3Repository;
import com.duoc.plataformatransportes.servicio.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl
        implements S3Service {

    private final S3Repository s3Repository;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public Asset subirArchivo(
            String key,
            File archivo
    ) {

        String url =
                s3Repository
                        .uploadFile(
                                bucketName,
                                key,
                                archivo
                        );

        return Asset.builder()
                .name(
                        archivo.getName()
                )
                .key(key)
                .url(
                        crearUrl(url)
                )
                .build();

    }

    @Override
    public byte[] descargarArchivo(
            String key
    ) {

        try {

            return s3Repository
                    .downloadFile(
                            bucketName,
                            key
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
    public void eliminarArchivo(
            String key
    ) {

        s3Repository
                .deleteObject(
                        bucketName,
                        key
                );

    }

    @Override
    public Asset actualizarArchivo(
            String key,
            File archivo
    ) {

        eliminarArchivo(
                key
        );

        return subirArchivo(
                key,
                archivo
        );

    }

    private URL crearUrl(
        String url
    ) {

        try {

            return URI
                    .create(
                            url
                    )
                    .toURL();

        } catch (
                Exception e
        ) {

            throw new RuntimeException(
                    e
            );

        }

    }

    @Override
     public List<Asset> consultarGuias(Integer anio, String transportista) {

                String prefijo =
                        "%d/%s/"
                                .formatted(
                                        anio,
                                        transportista
                                );

                return s3Repository
                        .listObjectsInBucket(
                                bucketName
                        )
                        .stream()
                        .filter(
                                asset ->
                                        asset.getKey()
                                                .startsWith(
                                                        prefijo
                                                )
                        )
                        .toList();

    }

}
