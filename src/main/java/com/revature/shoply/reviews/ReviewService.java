package com.revature.shoply.reviews;

import com.revature.shoply.models.Product;
import com.revature.shoply.models.Review;
import com.revature.shoply.models.User;
import com.revature.shoply.orders.exceptions.UnauthorizedUserActionException;
import com.revature.shoply.product.service.ProductService;
import com.revature.shoply.repositories.ReviewRepository;
import com.revature.shoply.reviews.dto.ReviewDTO;
import com.revature.shoply.reviews.exceptions.ReviewNotFoundException;
import com.revature.shoply.user.service.UserService;
import jakarta.transaction.Transactional;
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
        log.info("Creating review for product: {}", review.getProductId());

        User user = userService.findUserByIdAndValidate(UUID.fromString(review.getUserId()));
        Product product = productService.getProductById(UUID.fromString(review.getProductId()));

        Review newReview = new Review();
        newReview.setUser(user);
        newReview.setProduct(product);
        if (review.getTitle() == null || review.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Review titel cannot be NULL or BLANK");
        }
        newReview.setTitle(review.getTitle());
        if (review.getDescription() == null || review.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Review Description cannot be NULL or BLANK");
        }
        newReview.setDescription(review.getDescription());
        if (review.getRating() < 1 || review.getRating() > 6) {
            throw new IllegalArgumentException("Review rating must be between 1 and 5 stars");
        }
        newReview.setRating(review.getRating());

        return reviewRepository.save(newReview);
    }

    public List<Review> getReviewsByProduct(String productId) {
        log.info("Getting reviews for product: {}", productId);
        return reviewRepository.findAllByProduct_Id(UUID.fromString(productId));
    }

    public List<Review> getReviewsByCustomer(String userId) {
        log.info("Getting reviews for user");
        User user = userService.findUserByIdAndValidate(UUID.fromString(userId));
        return reviewRepository.findAllByUser_Id(user.getId());
    }

    public List<Review> getAllReviews() {
        log.info("Getting all reviews");
        return reviewRepository.findAll();
    }

    @Transactional
    public void deleteReview(String reviewId) {
        log.info("Deleting review: {}", reviewId);
        reviewRepository.deleteById(UUID.fromString(reviewId));
    }

    @Transactional
    public void deleteCustomerReview(String reviewId, UUID user_id) {
        log.info("Deleting review: {}", reviewId);
        Review review = reviewRepository.findById(UUID.fromString(reviewId)).orElseThrow(() ->
                new ReviewNotFoundException("Review not found"));
        if (!review.getUser().getId().equals(user_id)) {
            throw new UnauthorizedUserActionException("User does not have permission to delete this review");
        }
        reviewRepository.deleteByIdAndUser_Id(UUID.fromString(reviewId), user_id);

    }

    public Review updateReview(String reviewId, ReviewDTO review) {
        log.info("Updating review with id: {}", reviewId);

        Review oldReview = reviewRepository.findById(UUID.fromString(reviewId)).orElseThrow(()
                -> new ReviewNotFoundException("Review not found"));
        if (!review.getUserId().equals(oldReview.getUser().getId().toString())) {
            throw new UnauthorizedUserActionException("User does not have permission to update this review");
        }
        oldReview.setTitle(review.getTitle());
        oldReview.setDescription(review.getDescription());
        oldReview.setRating(review.getRating());
        return reviewRepository.save(oldReview);
    }
}
