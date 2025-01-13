package com.revature.shoply.orders;

import com.revature.shoply.models.Order;
import com.revature.shoply.orders.dto.CreateOrderDTO;
import com.revature.shoply.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/orders")
@CrossOrigin
public class OrderController {

    private final JwtUtil jwtUtil;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @Secured({"ADMIN", "STORE_OWNER"})
    @GetMapping("/v1/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Secured("CUSTOMER")
    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getOrdersByUserId(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token.substring(7));
        return ResponseEntity.ok(orderService.getOrdersByUserId(UUID.fromString(userId)));
    }

    @Secured("CUSTOMER")
    @GetMapping("/customer/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId, @RequestHeader("Authorization") String token) {
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(orderService.getOrderById(userId, UUID.fromString(orderId)));
    }

    @Secured("CUSTOMER")
    @PatchMapping("/customer/order/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId, @RequestHeader("Authorization") String token) {
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(orderService.cancelOrder(userId, UUID.fromString(orderId)));
    }

    @PostMapping("/customer/order/create")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO order, @RequestHeader("Authorization") String token) {
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(orderService.createOrder(userId, order));
    }

}
