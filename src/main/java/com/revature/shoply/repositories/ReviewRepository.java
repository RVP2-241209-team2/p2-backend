package com.revature.shoply.repositories;

import com.revature.shoply.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByProduct_Id(UUID product_id);

    List<Review> findAllByUser_Id(UUID user_id);

    void deleteByIdAndUser_Id(UUID id, UUID user_id);
}
