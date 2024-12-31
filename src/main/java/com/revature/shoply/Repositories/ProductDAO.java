package com.revature.shoply.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.shoply.models.Product;


@Repository
public interface ProductDAO extends JpaRepository<Product, UUID>{
    
}
