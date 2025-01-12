package com.revature.shoply.product.repository;

import com.revature.shoply.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {

    public Optional<Product> findByName(String name);

    public Optional<List<Product>> findByTags_TagName(String name);

    List<Product> findByNameContaining(String name);
}