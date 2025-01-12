package com.revature.shoply.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.shoply.product.service.S3Service;
import com.revature.shoply.product.DTO.PresignedUrlResponse;

@RestController
@RequestMapping("/api/s3")
@CrossOrigin(origins = "*")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(
            @RequestParam String fileName,
            @RequestParam String contentType) {
        String presignedUrl = s3Service.generatePresignedUrl(fileName, contentType);
        return ResponseEntity.ok(new PresignedUrlResponse(presignedUrl));
    }
}
