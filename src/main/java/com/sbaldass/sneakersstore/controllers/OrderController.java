package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.OrderStatus;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.dto.OrderRequest;
import com.sbaldass.sneakersstore.dto.OrderStatusRequest;
import com.sbaldass.sneakersstore.services.OrderDetailsService;
import com.sbaldass.sneakersstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place a new order
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Get order by ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Get orders for the current authenticated user (example endpoint)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getOrdersByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(orderService.getOrdersByCustomer(currentUser), HttpStatus.OK);
    }

    // Update order status (admin only)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusRequest statusRequest) {
        Order updatedOrder = orderService.updateOrderStatus(id, statusRequest);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
