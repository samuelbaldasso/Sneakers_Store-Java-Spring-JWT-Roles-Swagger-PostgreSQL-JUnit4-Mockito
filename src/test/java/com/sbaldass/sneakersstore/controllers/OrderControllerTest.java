package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.dto.OrderRequest;
import com.sbaldass.sneakersstore.dto.OrderStatusRequest;
import com.sbaldass.sneakersstore.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder() {
        OrderRequest orderRequest = new OrderRequest();
        Order order = new Order();
        order.setId(1L);

        when(orderService.placeOrder(orderRequest)).thenReturn(order);

        ResponseEntity<Order> response = orderController.placeOrder(orderRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).placeOrder(orderRequest);
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testGetOrdersByCustomer() {
        Authentication authentication = mock(Authentication.class);
        User currentUser = new User();
        currentUser.setId(1L);

        when(authentication.getPrincipal()).thenReturn(currentUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Order order = new Order();
        order.setId(1L);

        when(orderService.getOrdersByCustomer(currentUser)).thenReturn(Collections.singletonList(order));

        ResponseEntity<List<Order>> response = orderController.getOrdersByCustomer();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(order), response.getBody());
        verify(orderService, times(1)).getOrdersByCustomer(currentUser);
    }

    @Test
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        OrderStatusRequest statusRequest = new OrderStatusRequest();
        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);

        when(orderService.updateOrderStatus(orderId, statusRequest)).thenReturn(updatedOrder);

        ResponseEntity<Order> response = orderController.updateOrderStatus(orderId, statusRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, statusRequest);
    }
}

