package com.revature.shoply.customer.controller;

import com.amazonaws.Response;
import com.revature.shoply.models.Cart;
import com.revature.shoply.customer.service.CartService;
import com.revature.shoply.customer.DTOs.IncomingCartItemDTO;
import com.revature.shoply.login.service.LoginService;


import java.util.List;
import java.util.UUID;

import com.revature.shoply.models.CartItem;
import com.revature.shoply.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/cart")
public class CartController {
    private final CartService cartService;

    private final JwtUtil jwtUtil;

    @Autowired
    public CartController(CartService cartService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }


    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable UUID cartItemId){
        cartService.DeleteCartItemById(cartItemId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody IncomingCartItemDTO cartItemDTO, @RequestHeader("Authorization") String token){
        cartItemDTO.setUserId(UUID.fromString(jwtUtil.extractUserId(token.substring(7))));
        return ResponseEntity.ok(cartService.addToCart(cartItemDTO));
    }

    @GetMapping
    public ResponseEntity<Cart> viewCart(@RequestHeader("Authorization") String token) {
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(cartService.viewCart(userId));
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> viewCartItems(@RequestHeader("Authorization") String token) {
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(cartService.viewCartItems(userId));
    }

    @PatchMapping("/update/quantity")
    public ResponseEntity<CartItem> updateItemQuantity(@RequestHeader("Authorization") String token, @RequestBody IncomingCartItemDTO cartItemDTO) {
        cartItemDTO.setUserId(UUID.fromString(jwtUtil.extractUserId(token.substring(7))));
        return ResponseEntity.ok(cartService.updateItemQuantity(cartItemDTO));
    }
}
