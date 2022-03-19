package com.ecommerce.product.service.impl;

import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Discount;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.service.DiscountService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.ProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final DiscountService discountService;

    public ProductServiceImpl(@Autowired ProductRepository repository, @Autowired CategoryService categoryService,
                              @Autowired DiscountService discountService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.discountService = discountService;
    }

    @Override
    public Product createProduct(Product product) {
        product.setModifiedAt(LocalDateTime.now());
        product.setCreatedAt(LocalDateTime.now());

        Category category = null;
        Discount discount = null;

        if (product.getCategory() != null) {
            category = categoryService.findById(product.getCategory().getId());
        }

        if (product.getDiscount() != null) {
            discount = discountService.findById(product.getDiscount().getId());
        }

        product.setCategory(category);
        product.setDiscount(discount);

        return repository.save(product);
    }

    @Override
    public Product editProduct(Product product) { // todo - adicionar histórico de preços
        Product productSaved = repository.findById(product.getId()).orElseThrow(() -> new GenericException.NotFoundException("Product", product.getId()));

        Category category = categoryService.findById(product.getCategory().getId());
        Discount discount = discountService.findById(product.getDiscount().getId());

        productSaved.setDescription(product.getDescription());
        productSaved.setName(product.getName());
        productSaved.setPrice(product.getPrice());
        productSaved.setCategory(category);
        productSaved.setDiscount(discount);
        productSaved.setModifiedAt(LocalDateTime.now());

        return repository.save(productSaved);
    }

    @Override
    public Product findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));
        if (product.getDeletedAt() != null) {
            throw new ProductException.ProductDeleted(id);
        }
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));

        if (product.getDeletedAt() != null) {
            throw new ProductException.ProductDeleted(id);
        }

        product.setDeletedAt(LocalDateTime.now());
        product.setModifiedAt(LocalDateTime.now());

        repository.save(product);
    }

    @Override
    public List<Product> listProduct() {
        return repository.findAll();
    }
}
