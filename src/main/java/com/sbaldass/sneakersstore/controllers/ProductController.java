package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.Product;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.services.ProductService;
import com.sbaldass.sneakersstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Product>> allProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public void create(@RequestBody Product product) {
        productService.create(product);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public void update(@RequestBody Product product, @PathVariable Long id) {
        productService.update(product, id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Optional<Product> getById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
