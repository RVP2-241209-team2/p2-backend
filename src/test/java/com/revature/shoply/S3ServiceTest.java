package com.revature.shoply;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.revature.shoply.config.Parameters;
import com.revature.shoply.product.service.S3Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private Parameters params;

    @Mock
    private AmazonS3 s3Client;

    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        s3Service = new S3Service(s3Client, params);
        ReflectionTestUtils.setField(s3Service, "bucketName", "test-bucket");
    }

    @Test
    void generatePresignedUrl_ShouldReturnValidUrl() throws Exception {
        // Arrange
        String fileName = "test.jpg";
        String contentType = "image/jpeg";
        String expectedUrl = "https://test-bucket.s3.amazonaws.com/test.jpg";
        URL mockUrl = new URL(expectedUrl);

        when(s3Client.generatePresignedUrl(any(GeneratePresignedUrlRequest.class)))
                .thenReturn(mockUrl);

        String result = s3Service.generatePresignedUrl(fileName, contentType);

        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(s3Client, times(1))
                .generatePresignedUrl(any(GeneratePresignedUrlRequest.class));
    }

    @Test
    void generatePresignedUrl_WithNullFileName_ShouldThrowException() {
        String fileName = null;
        String contentType = "image/jpeg";

        assertThrows(IllegalArgumentException.class, () -> {
            s3Service.generatePresignedUrl(fileName, contentType);
        });
    }

    @Test
    void generatePresignedUrl_WithNullContentType_ShouldThrowException() {
        String fileName = "test.jpg";
        String contentType = null;

        assertThrows(IllegalArgumentException.class, () -> {
            s3Service.generatePresignedUrl(fileName, contentType);
        });
    }
}