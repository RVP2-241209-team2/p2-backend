package com.revature.shoply.orders.dto;

import com.revature.shoply.models.OrderItem;

import java.util.List;
import java.util.Map;

public class CreateOrderDTO {

    private String addressId;
    private String paymentId;
    private Map<String, Integer> orderItems;

    public CreateOrderDTO() {
    }

    public CreateOrderDTO(String addressId, String paymentId, Map<String, Integer> orderItems) {
        this.addressId = addressId;
        this.paymentId = paymentId;
        this.orderItems = orderItems;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, Integer> orderItems) {
        this.orderItems = orderItems;
    }
}
