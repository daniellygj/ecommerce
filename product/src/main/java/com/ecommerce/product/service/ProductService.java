package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product editProduct(Product product);

    Product findById(Long id);

    void deleteProduct(Long id);

    List<Product> listProduct();
    
}
