package com.socialsync.service;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class MinIoService {
    private static MinioClient minioClient;

    static {
        minioClient = MinioClient.builder()
                .endpoint("http://localhost:8091")
                .credentials("minio_access_key", "minio_secret_key")
                .build();
    }


    //https://davidgiard.com/using-the-minio-java-sdk
    public void writeToMinIO(String fileName, byte[] content) throws IllegalArgumentException {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket("images").build()
            );
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket("images")
                                .build()
                );
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("images")
                            .object(fileName)
                            .stream(new ByteArrayInputStream(content), content.length, -1)
                            .build()
            );

            System.out.println(fileName + " successfully uploaded to:");
            System.out.println("   container: images");
            System.out.println("   blob: " + fileName);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }


    public byte[] readFromMinIO(String fileName) throws IllegalArgumentException {
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("images")
                        .object(fileName)
                        .build()
        )) {
            // citire bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("Citire cu succes!");
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
            return null;
        }
    }
}
