package com.revature.shoply.customer.service;

import com.revature.shoply.repositories.CartDAO;
import com.revature.shoply.product.repository.ProductDAO;
import com.revature.shoply.repositories.CartItemDAO;
import com.revature.shoply.repositories.UserDAO;
import com.revature.shoply.models.Cart;
import com.revature.shoply.models.CartItem;
import com.revature.shoply.customer.DTOs.IncomingCartItemDTO;
import com.revature.shoply.models.Product;
import com.revature.shoply.models.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartDAO cartDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final CartItemDAO cartItemDAO;

    @Autowired
    public CartService(CartDAO cartDAO, ProductDAO productDAO, UserDAO userDAO, CartItemDAO cartItemDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Transactional
    public void deleteCartItemById(UUID cartItemId){
        CartItem item = cartItemDAO.findById(cartItemId).orElseThrow(() ->
                new RuntimeException("No Item found"));
        item.setProduct(null);
        cartItemDAO.deleteById(cartItemId);
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

    public List<CartItem> viewCartItems(UUID userId) {
        User user = userDAO.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        Cart cart = cartDAO.findById(user.getCart().getId()).orElseThrow(() ->
                new RuntimeException("Cart for user not found"));

        return cart.getCartItems();
    }

    public CartItem updateItemQuantity(IncomingCartItemDTO cartItemDTO) {
        User user = userDAO.findById(cartItemDTO.getUserId()).orElseThrow(() ->
                new RuntimeException("No User Found"));

        Cart cart = cartDAO.findById(user.getCart().getId()).orElseThrow(() ->
                new RuntimeException("No cart found"));

        Product product = productDAO.findById(cartItemDTO.getProductId()).orElseThrow(() ->
                new RuntimeException("Product not found"));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
            existingItem.setTotal(product.getPrice() * existingItem.getQuantity());
            updateCartTotal(cart);
            return cartItemDAO.save(existingItem);
        } else {
            throw new RuntimeException("Item not found in cart");
        }
    }

    private void updateCartTotal(Cart cart) {
        Cart updatedCart = cartDAO.findById(cart.getId()).orElseThrow(() ->
                new RuntimeException("No Cart found"));

        double sum = cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotal)
                .sum();

        updatedCart.setTotal(sum);
        cartDAO.save(updatedCart);
    }
}
