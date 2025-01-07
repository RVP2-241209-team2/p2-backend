package com.revature.shoply.product.repository;

import com.revature.shoply.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {

    public Optional<Product> findByName(String name);
}