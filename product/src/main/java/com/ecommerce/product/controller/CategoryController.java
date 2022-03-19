package com.ecommerce.product.controller;

import com.ecommerce.product.model.Category;
import com.ecommerce.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(@Autowired CategoryService categoryService) {
        this.service = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(service.createCategory(category));
    }

    @PutMapping
    public ResponseEntity<Category> editCategory(@RequestBody Category category) {
        return ResponseEntity.ok(service.editCategory(category));
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
    }

    @GetMapping
    public ResponseEntity<List<Category>> listCategory() {
        return ResponseEntity.ok(service.listCategory());
    }
}
