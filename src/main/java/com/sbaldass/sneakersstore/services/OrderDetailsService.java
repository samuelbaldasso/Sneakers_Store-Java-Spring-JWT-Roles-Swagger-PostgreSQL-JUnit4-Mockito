package com.sbaldass.sneakersstore.services;

import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.OrderDetail;
import com.sbaldass.sneakersstore.domain.Product;
import com.sbaldass.sneakersstore.dto.OrderItemRequest;
import com.sbaldass.sneakersstore.repository.OrderDetailRepository;
import com.sbaldass.sneakersstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public void saveOrderDetails(Order order, List<OrderItemRequest> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("Order details cannot be empty");
        }

        for (OrderItemRequest detailRequest : orderDetails) {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found."));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setPrice(product.getPrice());

            orderDetailRepository.save(orderDetail);
        }
    }
}
