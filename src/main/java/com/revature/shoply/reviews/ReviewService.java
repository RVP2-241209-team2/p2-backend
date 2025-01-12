package com.revature.shoply.reviews;

import com.revature.shoply.models.Product;
import com.revature.shoply.models.Review;
import com.revature.shoply.models.User;
import com.revature.shoply.product.service.ProductService;
import com.revature.shoply.reviews.dto.ReviewDTO;
import com.revature.shoply.user.service.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductService productService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Review createReview(ReviewDTO review) {
        log.info("Creating review for product: " + review.getProductId());

        User user = userService.findUserByIdAndValidate(UUID.fromString(review.getUserId()));
        Product product = productService.getProductById(UUID.fromString(review.getProductId()));

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setProduct(product);
        newReview.setTitle(review.getTitle());
        newReview.setDescription(review.getDescription());
        newReview.setRating(review.getRating());

        return reviewRepository.save(newReview);
    }

    public List<Review> getReviewsByProduct(String productId) {
        log.info("Getting reviews for product: " + productId);
        return reviewRepository.findAllByProduct_Id(UUID.fromString(productId));
    }

    public List<Review> getReviewsByCustomer(String userId) {
        log.info("Getting reviews for user: " + userId);
        User user = userService.findUserByIdAndValidate(UUID.fromString(userId));
        return reviewRepository.findAllByUser_Id(user.getId());
    }

    public List<Review> getAllReviews() {
        log.info("Getting all reviews");
        return reviewRepository.findAll();
    }

    public void deleteReview(String reviewId) {
        log.info("Deleting review: " + reviewId);
        reviewRepository.deleteById(UUID.fromString(reviewId));
    }

    public void deleteCustomerReview(String reviewId, UUID id) {
        log.info("Deleting review: " + reviewId + " for user: " + id);
        reviewRepository.deleteByIdAndUser_Id(UUID.fromString(reviewId), id);

    }
}
