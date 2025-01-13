package com.revature.shoply.product.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.revature.shoply.config.Parameters;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class S3Service {

    private final Parameters params;

    private String bucketName;

    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client, Parameters params) {
        this.s3Client = s3Client;
        this.params = params;
    }

    @PostConstruct
    public void init() {
        this.bucketName = params.getS3BucketName();
    }

    public String generatePresignedUrl(String fileName, String contentType) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (contentType == null || contentType.trim().isEmpty()) {
            throw new IllegalArgumentException("Content type cannot be null or empty");
        }
        // Set the presigned URL to expire after 5 minutes
        Date expiration = Date.from(Instant.now().plusSeconds(300));

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        // Set the content type so the uploaded file has the correct MIME type
        generatePresignedUrlRequest.setContentType(contentType);

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}