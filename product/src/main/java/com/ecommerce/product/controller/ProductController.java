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

    private final ModelMapper mapper;

    public ProductController(@Autowired ProductService productService) {
        this.service = productService;
        this.mapper = new ModelMapper();
    }

    @Operation(summary = "Create new product", description = "Returns a single product", tags = { "product" })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        Product productSaved = service.createProduct(mapper.map(product, Product.class));
        ProductDTO productDTO = mapper.map(productSaved, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @Operation(summary = "Edit a product", description = "Returns a single product", tags = { "product" })
    @PutMapping
    public ResponseEntity<ProductDTO> editProduct(@RequestBody ProductDTO product) {
        Product productSaved = service.editProduct(mapper.map(product, Product.class));
        ProductDTO productDTO = mapper.map(productSaved, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @Operation(summary = "Search product by id", description = "Returns a single product", tags = { "product" })
    @GetMapping("id")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("id") Long id) {
        Product productSaved = service.findById(id);
        ProductDTO productDTO = mapper.map(productSaved, ProductDTO.class);

        return ResponseEntity.ok(productDTO);
    }

    @Operation(summary = "Delete a product", description = "Returns a empty body", tags = { "product" })
    @DeleteMapping("id")
    public void deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
    }

    @Operation(summary = "List all products", description = "Returns a product list", tags = { "product" })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProduct() {
        List<Product> productList = service.listProduct();
        Type listType = new TypeToken<List<ProductDTO>>(){}.getType();
        List<ProductDTO> productDTOList = mapper.map(productList, listType);

        return ResponseEntity.ok(productDTOList);
    }
}
