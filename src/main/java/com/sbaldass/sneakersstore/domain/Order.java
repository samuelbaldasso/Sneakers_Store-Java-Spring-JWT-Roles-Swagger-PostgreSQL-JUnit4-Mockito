package com.sbaldass.sneakersstore.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    private LocalDateTime orderDate = LocalDateTime.now();

    private String payment;

    @ManyToOne
    private Product product;


}
