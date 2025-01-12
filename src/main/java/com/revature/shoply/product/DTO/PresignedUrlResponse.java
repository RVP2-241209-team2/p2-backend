package com.revature.shoply.product.DTO;

public class PresignedUrlResponse {
    private String presignedUrl;

    public PresignedUrlResponse(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public void setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }
}