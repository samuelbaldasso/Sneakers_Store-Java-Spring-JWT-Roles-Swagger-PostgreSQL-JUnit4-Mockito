package com.sbaldass.sneakersstore.repository;

import com.sbaldass.sneakersstore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
