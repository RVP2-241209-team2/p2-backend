//package com.revature.shoply.controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.revature.shoply.models.Order;
//import com.revature.shoply.services.OrderService;
//
//@RestController
//@RequestMapping("/api/v1/orders")
//public class OrderController {
//
//    private OrderService orderService;
//
//    @Autowired
//    public OrderController(OrderService orderService){this.orderService=orderService;}
//
//     @GetMapping
//    public ResponseEntity<List<Order>> getAllOrders() {
//
//        List<Order> orders = orderService.getAllOrders();
//
//        return ResponseEntity.ok(orders);
//    }
//
//}
