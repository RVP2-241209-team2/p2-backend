package com.rvp2.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="payment_details_id")
    private PaymentDetails paymentDetails;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

}
