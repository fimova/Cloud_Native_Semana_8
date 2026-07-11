package com.duoc.plataformatransportes.repositorio;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.duoc.plataformatransportes.modelo.Asset;

public interface S3Repository {

    byte[] downloadFile(
            String bucket,
            String key
    ) throws IOException;

    String uploadFile(
            String bucket,
            String key,
            File file
    );

    void deleteObject(
            String bucket,
            String key
    );

    void moveObject(
            String bucket,
            String sourceKey,
            String destinationKey
    );

    List<Asset> listObjectsInBucket(
            String bucket
    );

}
