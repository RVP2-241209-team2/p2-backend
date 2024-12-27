package com.rvp2.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double total;

}
