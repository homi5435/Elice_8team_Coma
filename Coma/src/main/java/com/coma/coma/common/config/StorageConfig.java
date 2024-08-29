package com.coma.coma.common.config;

import com.coma.coma.common.service.AwsS3Service;
import com.coma.coma.common.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Bean
    public StorageService storageService() {
        return new AwsS3Service(s3Client(), "coma-image-bucket");
    }


    // 아마존 S3 Client 활용
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
