package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO product);

    ProductDTO editProduct(ProductDTO product);

    ProductDTO findById(Long id);

    void deleteProduct(Long id);

    List<ProductDTO> listProduct();
}
