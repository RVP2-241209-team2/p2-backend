package com.revature.shoply.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.shoply.models.Order;


@Repository
public interface OrderDAO extends JpaRepository<Order, UUID>{
    
}
