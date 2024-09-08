package com.sbaldass.sneakersstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
    private String paymentMethod;
    private String shippingAddress;
    private String billingAddress;
}