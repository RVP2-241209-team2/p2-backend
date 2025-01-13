package com.revature.shoply.orders;

import com.revature.shoply.models.Order;
import com.revature.shoply.orders.dto.CreateOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Secured({"ADMIN", "STORE_OWNER"})
    @GetMapping("/v1/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/customer/{userId}/orders")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(UUID.fromString(userId)));
    }

    @GetMapping("/customer/order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(UUID.fromString(orderId)));
    }

    @PatchMapping("/customer/order/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(UUID.fromString(orderId)));
    }

    @PostMapping("/customer/{userId}/order/create")
    public ResponseEntity<Order> createOrder(@PathVariable String userId, @RequestBody CreateOrderDTO order) {
        return ResponseEntity.ok(orderService.createOrder(UUID.fromString(userId), order));
    }

}
