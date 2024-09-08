package com.sbaldass.sneakersstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.User;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testFindByUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findByUser(user)).thenReturn(orders);

        // Act
        List<Order> result = orderRepository.findByUser(user);

        // Assert
        assertEquals(2, result.size());
        assertEquals(order1, result.get(0));
        assertEquals(order2, result.get(1));
        verify(orderRepository, times(1)).findByUser(user);
    }
}
