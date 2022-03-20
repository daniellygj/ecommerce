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

    private final ModelMapper mapper;

    public CategoryController(@Autowired CategoryService categoryService) {
        this.service = categoryService;
        this.mapper = new ModelMapper();
    }

    @Operation(summary = "Create category", description = "Returns a single category", tags = { "category" })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        Category categorySaved = service.createCategory(mapper.map(category, Category.class));
        CategoryDTO categoryDTO = mapper.map(categorySaved, CategoryDTO.class);

        return ResponseEntity.ok(categoryDTO);
    }

    @Operation(summary = "Edit category", description = "Returns a single category", tags = { "category" })
    @PutMapping
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO category) {
        Category categoryEdited = service.editCategory(mapper.map(category, Category.class));
        CategoryDTO categoryDTO = mapper.map(categoryEdited, CategoryDTO.class);

        return ResponseEntity.ok(categoryDTO);
    }

    @Operation(summary = "Search category by id", description = "Returns a single category", tags = { "category" })
    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
        Category categoryFound = service.findById(id);
        CategoryDTO categoryDTO = mapper.map(categoryFound, CategoryDTO.class);

        return ResponseEntity.ok(categoryDTO);
    }

    @Operation(summary = "Delete category", description = "Return empty body", tags = { "category" })
    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
    }

    @Operation(summary = "List all categories", description = "Returns a list of categories", tags = { "category" })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategory() {
        List<Category> categoryList = service.listCategory();
        Type listType = new TypeToken<List<BrandDTO>>(){}.getType();
        List<CategoryDTO> categoryDTOList =  mapper.map(categoryList, listType);

        return ResponseEntity.ok(categoryDTOList);
    }
}
