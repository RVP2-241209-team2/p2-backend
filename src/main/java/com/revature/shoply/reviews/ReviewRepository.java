package com.revature.shoply.reviews;

import com.revature.shoply.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByProduct_Id(UUID uuid);

    List<Review> findAllByUser_Id(UUID id);

    void deleteByIdAndUser_Id(UUID uuid, UUID id);
}
