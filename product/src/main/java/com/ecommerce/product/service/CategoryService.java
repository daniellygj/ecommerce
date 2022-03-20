package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO editCategory(CategoryDTO category);

    CategoryDTO findById(Long id);

    void deleteCategory(Long id);

    List<CategoryDTO> listCategory();
}
