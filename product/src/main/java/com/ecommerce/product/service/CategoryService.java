package com.ecommerce.product.service;

import com.ecommerce.product.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category editCategory(Category category);

    Category findById(Long id);

    void deleteCategory(Long id);

    List<Category> listCategory();
}
