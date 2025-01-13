package com.revature.shoply.reviews.dto;

public class ReviewDTO {

    private String userId;
    private String productId;
    private String title;
    private String description;
    private int rating;

    public ReviewDTO() {
    }

    public ReviewDTO(String userId, String productId, String title, String description, int rating) {
        this.userId = userId;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
