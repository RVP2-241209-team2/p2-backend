package com.revature.shoply.product.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class S3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String generatePresignedUrl(String fileName, String contentType) {
        // Generate a unique file name to prevent overwrites
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName.replaceAll(" ", "_");
        
        // Set the presigned URL to expire after 5 minutes
        Date expiration = Date.from(Instant.now().plusSeconds(300));
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, uniqueFileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        
        // Set the content type so the uploaded file has the correct MIME type
        generatePresignedUrlRequest.setContentType(contentType);

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}