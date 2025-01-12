package com.revature.shoply.product.DTO;

public class PresignedUrlResponse {
    private String presignedUrl;
    private String finalImageUrl;
    private String key;

    public PresignedUrlResponse(String presignedUrl, String finalImageUrl, String key) {
        this.presignedUrl = presignedUrl;
        this.finalImageUrl = finalImageUrl;
        this.key = key;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public void setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }

    public String getFinalImageUrl() {
        return finalImageUrl;
    }

    public void setFinalImageUrl(String finalImageUrl) {
        this.finalImageUrl = finalImageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}