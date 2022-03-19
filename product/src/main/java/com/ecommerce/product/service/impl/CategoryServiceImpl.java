package com.ecommerce.product.service.impl;

import com.ecommerce.product.model.Category;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.utils.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(@Autowired CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category createCategory(Category category) {
        category.setModifiedAt(LocalDateTime.now());
        category.setCreatedAt(LocalDateTime.now());

        return repository.save(category);
    }

    @Override
    public Category editCategory(Category category) {
        Category categorySaved = repository.findById(category.getId()).orElseThrow(() -> new GenericException.NotFoundException("Category", category.getId()));

        categorySaved.setName(category.getName());
        categorySaved.setDescription(category.getDescription());
        categorySaved.setModifiedAt(LocalDateTime.now());

        return repository.save(categorySaved);
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Category", id));
    }

    @Override
    public void deleteCategory(Long id) {
        Category categorySaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Category", id));

        categorySaved.setDeletedAt(LocalDateTime.now());

        repository.save(categorySaved);
    }

    @Override
    public List<Category> listCategory() {
        return repository.findAll();
    }
}
