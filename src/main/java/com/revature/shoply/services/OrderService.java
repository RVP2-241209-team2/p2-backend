package com.revature.shoply.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.shoply.repositories.OrderDAO;
import com.revature.shoply.models.Order;

@Service
public class OrderService {

    private final OrderDAO  orderDAO;


    @Autowired
    public OrderService(OrderDAO orderDAO){this.orderDAO=orderDAO;}

    public List<Order> getAllOrders(){return orderDAO.findAll();}
    
}
