package com.duoc.plataformatransportes.repositorio.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.duoc.plataformatransportes.repositorio.S3Repository;
import com.duoc.plataformatransportes.modelo.Asset;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class S3RepositoryImpl
        implements S3Repository {

    private final S3Client s3Client;

    @Override
    public byte[] downloadFile(
            String bucket,
            String key
    )
            throws IOException {

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

        ResponseBytes<GetObjectResponse>
                response =
                s3Client.getObjectAsBytes(
                        request
                );

        return response.asByteArray();

    }

    @Override
    public String uploadFile(
            String bucket,
            String key,
            File file
    ) {

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

        s3Client.putObject(
                request,
                RequestBody.fromFile(
                        file
                )
        );

        return String.format(
                "https://%s.s3.amazonaws.com/%s",
                bucket,
                key
        );

    }

    @Override
    public void deleteObject(
            String bucket,
            String key
    ) {

        DeleteObjectRequest request =
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build();

        s3Client.deleteObject(
                request
        );

    }

    @Override
    public void moveObject(
            String bucket,
            String sourceKey,
            String destinationKey
    ) {

        CopyObjectRequest copy =
                CopyObjectRequest.builder()
                        .sourceBucket(bucket)
                        .sourceKey(sourceKey)
                        .destinationBucket(bucket)
                        .destinationKey(destinationKey)
                        .build();

        s3Client.copyObject(
                copy
        );

        deleteObject(
                bucket,
                sourceKey
        );

    }

    @Override
    public List<Asset> listObjectsInBucket(
            String bucket
    ) {

        ListObjectsV2Request request =
                ListObjectsV2Request
                        .builder()
                        .bucket(bucket)
                        .build();

        return s3Client
                .listObjectsV2(
                        request
                )
                .contents()
                .stream()
                .map(
                        object ->

                                Asset.builder()
                                        .name(
                                                object.key()
                                        )
                                        .key(
                                                object.key()
                                        )
                                        .build()

                )
                .toList();

    }

}
