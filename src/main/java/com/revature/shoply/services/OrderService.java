package com.revature.shoply.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.shoply.repositories.OrderDAO;
import com.revature.shoply.models.Order;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderDAO  orderDAO;


    @Autowired
    public OrderService(OrderDAO orderDAO){this.orderDAO=orderDAO;}

    public List<Order> getAllOrders(){
        log.info("Getting all orders");
        return orderDAO.findAll();
    }
    
}
