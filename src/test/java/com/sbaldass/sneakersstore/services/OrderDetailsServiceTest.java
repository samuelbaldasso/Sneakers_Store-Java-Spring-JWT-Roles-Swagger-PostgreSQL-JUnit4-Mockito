package com.sbaldass.sneakersstore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.OrderDetail;
import com.sbaldass.sneakersstore.domain.Product;
import com.sbaldass.sneakersstore.repository.OrderDetailRepository;
import com.sbaldass.sneakersstore.repository.ProductRepository;
import com.sbaldass.sneakersstore.dto.OrderItemRequest;

@ExtendWith(MockitoExtension.class)
public class OrderDetailsServiceTest {

    @InjectMocks
    private OrderDetailsService orderDetailsService;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ProductRepository productRepository;

    private Order order;
    private Product product;
    private OrderItemRequest orderItemRequest;

    @BeforeEach
    public void setUp() {
        order = new Order();  // Initialize with default values or mock if needed
        product = new Product();
        product.setId(1L);
        product.setPrice(100.0);

        orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1L);
        orderItemRequest.setQuantity(2);
    }

    @Test
    public void testSaveOrderDetails() {
        List<OrderItemRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderItemRequest);

        when(productRepository.findById(orderItemRequest.getProductId())).thenReturn(Optional.of(product));

        orderDetailsService.saveOrderDetails(order, orderDetails);

        verify(productRepository, times(1)).findById(orderItemRequest.getProductId());
        verify(orderDetailRepository, times(1)).save(any(OrderDetail.class));
    }

    @Test
    public void testSaveOrderDetailsWithEmptyList() {
        List<OrderItemRequest> orderDetails = new ArrayList<>();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderDetailsService.saveOrderDetails(order, orderDetails);
        });

        assertEquals("Order details cannot be empty", thrown.getMessage());
    }

    @Test
    public void testSaveOrderDetailsWithProductNotFound() {
        List<OrderItemRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderItemRequest);

        when(productRepository.findById(orderItemRequest.getProductId())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            orderDetailsService.saveOrderDetails(order, orderDetails);
        });

        assertEquals("Product not found.", thrown.getMessage());
    }
}
