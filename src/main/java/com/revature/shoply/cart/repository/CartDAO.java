package com.revature.shoply.Repositories;

import com.revature.shoply.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {

}
