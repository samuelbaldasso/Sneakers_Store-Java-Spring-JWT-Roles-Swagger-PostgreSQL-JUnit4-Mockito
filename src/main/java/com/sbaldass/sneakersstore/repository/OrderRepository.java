package com.sbaldass.sneakersstore.repository;

import com.sbaldass.sneakersstore.domain.Order;
import com.sbaldass.sneakersstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User customer);
}
