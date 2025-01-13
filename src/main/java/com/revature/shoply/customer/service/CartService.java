package com.revature.shoply.customer.service;

import com.revature.shoply.repositories.CartDAO;
import com.revature.shoply.product.repository.ProductDAO;
import com.revature.shoply.repositories.UserDAO;
import com.revature.shoply.models.Cart;
import com.revature.shoply.models.CartItem;
import com.revature.shoply.customer.DTOs.IncomingCartItemDTO;
import com.revature.shoply.models.Product;
import com.revature.shoply.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    @Autowired
    public CartService(CartDAO cartDAO, ProductDAO productDAO, UserDAO userDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    public void DeleteCartItemById(UUID cartItemId){
        cartDAO.deleteById(cartItemId);
    }

    public Cart addToCart(IncomingCartItemDTO cartItemDTO) {
        log.info("Adding item to cart");

        if (cartItemDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        User user = userDAO.findById(cartItemDTO.getUserId()).orElseThrow(() -> {
            throw new IllegalArgumentException("No user found with ID " + cartItemDTO.getUserId());
        });

        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
            cart.setTotal(0.0);
            user.setCart(cart);
        }

        Product product = productDAO.findById(cartItemDTO.getProductId()).orElseThrow(() -> {
           throw new IllegalArgumentException("No product found with ID " + cartItemDTO.getProductId());
        });

        if (product.getQuantity() < cartItemDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough product in stock");
        }

        boolean itemExists = false;

        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
                item.setTotal(item.getQuantity() * product.getPrice());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(cartItemDTO.getQuantity());
            newItem.setTotal(cartItemDTO.getQuantity() * product.getPrice());
            cart.getCartItems().add(newItem);
        }

        double newTotal = 0.0;

        for (CartItem item : cart.getCartItems()) {
            newTotal += item.getTotal();
        }

        cart.setTotal(newTotal);

        return cartDAO.save(cart);
    }

    public Cart viewCart(UUID userId) {
        User user = userDAO.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        return cartDAO.findById(user.getCart().getId()).orElseThrow(() ->
                new RuntimeException("Cart not found"));
    }
}
