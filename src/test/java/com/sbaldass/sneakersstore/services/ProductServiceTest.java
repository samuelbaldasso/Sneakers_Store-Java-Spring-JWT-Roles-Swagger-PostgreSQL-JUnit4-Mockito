package com.sbaldass.sneakersstore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sbaldass.sneakersstore.domain.Product;
import com.sbaldass.sneakersstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private Product productDTO;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setStock(100);
        product.setPrice(9.99);

        productDTO = new Product();
        productDTO.setName("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setStock(150);
        productDTO.setPrice(19.99);
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Updated Product", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.update(productDTO, 1L);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals("Test Product", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }
}
