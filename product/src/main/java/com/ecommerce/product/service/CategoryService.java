package com.ecommerce.product.service;

import com.ecommerce.product.model.Category;

public interface CategoryService {

    Category createBrand(Category category);

    Category editBrand(Category category);

    void deleteCategory(Long id);
}
