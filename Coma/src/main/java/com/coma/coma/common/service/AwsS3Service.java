package com.coma.coma.common.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AwsS3Service implements StorageService{


    private final S3Client s3Client;
    private final String bucketName;

    public AwsS3Service(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    private String generateFileName(String username, String originalFileName) {
        LocalDateTime now = LocalDateTime.now();
        return username + "_" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + originalFileName;

    }

    @Override
    public String uploadFile(MultipartFile file, String username) {
        String filename = generateFileName(username, file.getOriginalFilename());
        try {

            // Convert MultipartFile to InputStream
            InputStream inputStream = file.getInputStream();

            // Create PutObjectRequest using builder
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .contentType(file.getContentType())
                    .build();

            // Upload file to S3
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // Return the URL of the uploaded file
            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(filename)).toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

    }

    @Override
    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();    }

    @Override
    public void deleteFile(String fileUrl) {
        String fileName = extractFileNameFromUrl(fileUrl);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    private String extractFileNameFromUrl(String fileUrl) {
        // S3 URL에서 파일 키를 추출하는 로직
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            // 경로에서 '/' 제거
            return path.startsWith("/") ? path.substring(1) : path;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
    }
}
