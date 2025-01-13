package com.revature.shoply.orders;

import com.revature.shoply.models.*;
import com.revature.shoply.models.enums.OrderStatus;
import com.revature.shoply.orders.dto.CreateOrderDTO;
import com.revature.shoply.repositories.OrderItemRepository;
import com.revature.shoply.repositories.OrderRepository;
import com.revature.shoply.product.service.ProductService;
import com.revature.shoply.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;


    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(UUID id) {
        return orderRepository.findByUserId(id);
    }

    public Order getOrderById(UUID userId, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Order with id {} not found" + orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Order with id {}, does not belong to user with id {}" + orderId + userId);
        }
        return orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Order with id " + orderId + " not found"));
    }

    public Order cancelOrder(UUID userId, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new RuntimeException("Order with id {} not found" + orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Order with id {}, does not belong to user with id {}" + orderId + userId);
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order createOrder(UUID userId, CreateOrderDTO order) {
        User user = userService.findUserByIdAndValidate(userId);
        Address address = userService.findAddressByIdAndValidate(UUID.fromString(order.getAddressId()));
        PaymentDetails paymentDetails = userService.findPayMethodByIdAndValidate(UUID.fromString(order.getPaymentId()));

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setShippingAddress(address);
        newOrder.setPaymentDetails(paymentDetails);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setTotal(0);
        newOrder.setDate(String.valueOf(new java.util.Date()));

        Map<String, Integer> items = order.getOrderItems();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            Product product = productService.getProductById(UUID.fromString(entry.getKey()));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItem.setOrder(newOrder);
            orderItem.setTotal(product.getPrice() * entry.getValue());
            orderItemRepository.save(orderItem);
            newOrder.setTotal(newOrder.getTotal() + orderItem.getTotal());
        }

        return orderRepository.save(newOrder);

    }

}
