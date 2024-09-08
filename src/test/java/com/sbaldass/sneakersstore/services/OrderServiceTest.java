package com.sbaldass.sneakersstore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sbaldass.sneakersstore.domain.*;
import com.sbaldass.sneakersstore.dto.OrderStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sbaldass.sneakersstore.dto.OrderItemRequest;
import com.sbaldass.sneakersstore.dto.OrderRequest;
import com.sbaldass.sneakersstore.repository.OrderDetailRepository;
import com.sbaldass.sneakersstore.repository.OrderRepository;
import com.sbaldass.sneakersstore.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailsService orderDetailsService;

    private User user;
    private Order order;
    private OrderRequest orderRequest;
    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(200.0);

        orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrder(order);
        orderDetail.setProduct(new Product()); // Initialize with default or mock values
        orderDetail.setQuantity(2);
        orderDetail.setPrice(100.0);

        orderRequest = new OrderRequest();
        orderRequest.setUserId(user.getId());
        orderRequest.setPaymentMethod("Credit Card");
        orderRequest.setShippingAddress("123 Main St");
        orderRequest.setBillingAddress("456 Oak St");
        orderRequest.setItems(new ArrayList<>()); // Add OrderItemRequest if needed
    }

    @Test
    public void testPlaceOrder() {
        when(userRepository.findById(orderRequest.getUserId())).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderDetailRepository.findByOrderId(order.getId())).thenReturn(List.of(orderDetail));
        doNothing().when(orderDetailsService).saveOrderDetails(any(Order.class), anyList());

        Order result = orderService.placeOrder(orderRequest);

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getTotalPrice(), result.getTotalPrice());
        verify(userRepository, times(1)).findById(orderRequest.getUserId());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderDetailsService, times(1)).saveOrderDetails(any(Order.class), anyList());
        verify(orderDetailRepository, times(1)).findByOrderId(order.getId());
    }

    @Test
    public void testGetOrderById() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(order.getId());

        assertEquals(order.getId(), result.getId());
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    public void testGetOrdersByCustomer() {
        List<Order> orders = List.of(order);
        when(orderRepository.findByUser(user)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCustomer(user);

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository, times(1)).findByUser(user);
    }

    @Test
    public void testUpdateOrderStatus() {
        String newStatus = "SHIPPED";
        OrderStatusRequest statusRequest = new OrderStatusRequest();
        statusRequest.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(order.getId(), statusRequest);

        assertEquals(newStatus, result.getStatus());
        verify(orderRepository, times(1)).findById(order.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
