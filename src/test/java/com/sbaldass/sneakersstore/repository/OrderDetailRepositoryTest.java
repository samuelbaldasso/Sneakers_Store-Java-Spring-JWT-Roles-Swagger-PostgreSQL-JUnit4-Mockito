package com.sbaldass.sneakersstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.sbaldass.sneakersstore.domain.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sbaldass.sneakersstore.domain.OrderDetail;

@ExtendWith(MockitoExtension.class)
public class OrderDetailRepositoryTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testFindByOrderId() {
        // Arrange
        Long orderId = 1L;
        OrderDetail detail1 = new OrderDetail();
        detail1.setId(1L);
        detail1.setOrder(new Order());
        OrderDetail detail2 = new OrderDetail();
        detail2.setId(2L);
        detail2.setOrder(new Order());
        List<OrderDetail> orderDetails = Arrays.asList(detail1, detail2);

        when(orderDetailRepository.findByOrderId(orderId)).thenReturn(orderDetails);

        // Act
        List<OrderDetail> result = orderDetailRepository.findByOrderId(orderId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(detail1, result.get(0));
        assertEquals(detail2, result.get(1));
        verify(orderDetailRepository, times(1)).findByOrderId(orderId);
    }
}
