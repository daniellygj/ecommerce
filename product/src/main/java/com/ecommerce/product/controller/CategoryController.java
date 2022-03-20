package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.controller.dto.CategoryDTO;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;


    public CategoryController(@Autowired CategoryService categoryService) {
        this.service = categoryService;
    }

    @Operation(summary = "Create category", description = "Returns a single category", tags = { "category" })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(service.createCategory(category));
    }

    @Operation(summary = "Edit category", description = "Returns a single category", tags = { "category" })
    @PutMapping
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(service.editCategory(category));
    }

    @Operation(summary = "Search category by id", description = "Returns a single category", tags = { "category" })
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Delete category", description = "Return empty body", tags = { "category" })
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
    }

    @Operation(summary = "List all categories", description = "Returns a list of categories", tags = { "category" })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategory() {
        return ResponseEntity.ok(service.listCategory());
    }
}
