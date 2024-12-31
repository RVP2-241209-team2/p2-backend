package com.revature.shoply.product.repository;

import java.util.List;
import com.revature.shoply.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {

    public Optional<List<Product>> findByName(String name);

    public Optional<List<Product>> findByTags_TagName(String name);

    List<Product> findByPriceGreaterThan(double price);

    List<Product> findByRatingGreaterThanEqual(double rating);

    @Query("SELECT AVG(p.rating) FROM Product p")
    double getAverageRating();

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findProductsInPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
}
