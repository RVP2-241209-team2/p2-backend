package com.revature.shoply.reviews;

import com.revature.shoply.models.Review;
import com.revature.shoply.reviews.dto.ReviewDTO;
import com.revature.shoply.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;

    public ReviewController(ReviewService reviewService, JwtUtil jwtUtil) {
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }

    @Secured("CUSTOMER")
    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO review, @RequestHeader("Authorization") String token) {
        review.setUserId(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @Secured("CUSTOMER")
    @GetMapping("customer/my-reviews")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reviewService.getReviewsByCustomer(jwtUtil.extractUserId(token.substring(7))));
    }

    @Secured("ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted");
    }

    @Secured("CUSTOMER")
    @DeleteMapping("/customer/delete/{reviewId}")
    public ResponseEntity<String> deleteCustomerReview(@PathVariable String reviewId, @RequestHeader("Authorization") String token) {
        reviewService.deleteCustomerReview(reviewId, UUID.fromString(jwtUtil.extractUserId(token.substring(7))));
        return ResponseEntity.ok("Review deleted");
    }

    @Secured("CUSTOMER")
    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable String reviewId, @RequestBody ReviewDTO review, @RequestHeader("Authorization") String token) {
        review.setUserId(jwtUtil.extractUserId(token.substring(7));
        return ResponseEntity.ok(reviewService.updateReview(reviewId, review));
    }

}
