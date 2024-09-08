package com.sbaldass.sneakersstore.services;

import com.sbaldass.sneakersstore.domain.*;
import com.sbaldass.sneakersstore.dto.OrderRequest;
import com.sbaldass.sneakersstore.dto.OrderStatusRequest;
import com.sbaldass.sneakersstore.repository.OrderDetailRepository;
import com.sbaldass.sneakersstore.repository.OrderRepository;
import com.sbaldass.sneakersstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailsService orderDetailsService;

    public Order placeOrder(OrderRequest orderRequest) {
        User customer = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        Order order = new Order();
        order.setUser(customer);
        order.setOrderDate(LocalDateTime.now());

        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setBillingAddress(orderRequest.getBillingAddress());

        Order savedOrder = orderRepository.save(order);

        orderDetailsService.saveOrderDetails(savedOrder, orderRequest.getItems());

        Double totalPrice = orderDetailRepository.findByOrderId(savedOrder.getId())
                .stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                .sum();
        savedOrder.setTotalPrice(totalPrice);

        return orderRepository.save(savedOrder);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));
    }

    public List<Order> getOrdersByCustomer(User customer) {
        return orderRepository.findByUser(customer);
    }

    public Order updateOrderStatus(Long id, OrderStatusRequest status) {
        // Fetch the order
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found."));

        // Update the status
        order.setStatus(status.getStatus());

        // Save the updated order
        return orderRepository.save(order);
    }
}
