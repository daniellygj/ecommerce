package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;


    public ProductController(@Autowired ProductService productService) {
        this.service = productService;
    }

    @Operation(summary = "Create new product", description = "Returns a single product", tags = { "product" })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.createProduct(productDTO));
    }

    @Operation(summary = "Edit a product", description = "Returns a single product", tags = { "product" })
    @PutMapping
    public ResponseEntity<ProductDTO> editProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(service.editProduct(product));
    }

    @Operation(summary = "Search product by id", description = "Returns a single product", tags = { "product" })
    @GetMapping("id")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Delete a product", description = "Returns a empty body", tags = { "product" })
    @DeleteMapping("id")
    public void deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
    }

    @Operation(summary = "List all products", description = "Returns a product list", tags = { "product" })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProduct() {
        return ResponseEntity.ok(service.listProduct());
    }
}
