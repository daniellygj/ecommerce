package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(@Autowired ProductService productService) {
        this.service = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.createProduct(product));
    }

    @PutMapping
    public ResponseEntity<Product> editProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.editProduct(product));
    }

    @GetMapping("id")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("id")
    public void deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct() {
        return ResponseEntity.ok(service.listProduct());
    }
}
