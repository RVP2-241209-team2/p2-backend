package com.revature.shoply.cart.service;

import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.revature.shoply.cart.repository.CartDAO;

public class CartService {
    
    private final CartDAO cartDAO;

    @Autowired
    public CartService(CartDAO cartDAO) { this.cartDAO = cartDAO; }


    public void DeleteCartItemById(UUID cartItemId){
        cartDAO.deleteById(cartItemId);
    }

}
