package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.Product;
import com.sbaldass.sneakersstore.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Name");

        when(productService.findAll()).thenReturn(Collections.singletonList(product));

        ResponseEntity<List<Product>> response = productController.allProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(product), response.getBody());
        verify(productService, times(1)).findAll();
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("New Product");

        productController.create(product);

        verify(productService, times(1)).create(product);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setName("Updated Product");

        productController.update(product, productId);

        verify(productService, times(1)).update(product, productId);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productController.delete(productId);

        verify(productService, times(1)).delete(productId);
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product Name");

        when(productService.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> response = productController.getById(productId);

        assertEquals(Optional.of(product), response);
        verify(productService, times(1)).findById(productId);
    }
}
