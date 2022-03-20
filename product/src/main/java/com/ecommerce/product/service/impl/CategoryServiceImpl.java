package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.controller.dto.CategoryDTO;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.utils.exception.GenericException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final ModelMapper mapper;

    public CategoryServiceImpl(@Autowired CategoryRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        categoryDTO.setModifiedAt(LocalDateTime.now());
        categoryDTO.setCreatedAt(LocalDateTime.now());

        Category categoryToSave = mapper.map(categoryDTO, Category.class);
        Category category = repository.save(categoryToSave);

        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO editCategory(CategoryDTO categoryDTO) {
        Category categoryFound = repository.findById(categoryDTO.getId()).orElseThrow(() -> new GenericException.NotFoundException("Category", categoryDTO.getId()));

        categoryFound.setName(categoryDTO.getName());
        categoryFound.setDescription(categoryDTO.getDescription());
        categoryFound.setModifiedAt(LocalDateTime.now());

        Category categoryToSave = mapper.map(categoryDTO, Category.class);
        Category category = repository.save(categoryToSave);

        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Category", id));

        return mapper.map(category, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category categorySaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Category", id));

        categorySaved.setDeletedAt(LocalDateTime.now());

        repository.save(categorySaved);
    }

    @Override
    public List<CategoryDTO> listCategory() {
        List<Category> categoryList = repository.findAll();
        Type listType = new TypeToken<List<BrandDTO>>(){}.getType();
        List<CategoryDTO> categoryDTOList =  mapper.map(categoryList, listType);
        return categoryDTOList;
    }
}
