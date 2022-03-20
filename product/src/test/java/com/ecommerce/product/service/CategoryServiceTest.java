package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.CategoryDTO;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.CategoryTestBuilder;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.impl.CategoryServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    private CategoryService categoryService;

    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(repository);
        this.mapper = new ModelMapper();
    }

    @Test
    public void CreateCategory_shouldSucceed() {
        Category categoryCpy = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);

        when(repository.save(isA(Category.class))).thenReturn(category);

        categoryService.createCategory(categoryDTO);

        assertEquals(categoryDTO.getDescription(), categoryCpy.getDescription());
        assertEquals(categoryDTO.getName(), categoryCpy.getName());
        assertNotEquals(categoryDTO.getCreatedAt(), categoryCpy.getCreatedAt());
        assertNotEquals(categoryDTO.getModifiedAt(), categoryCpy.getModifiedAt());
        assertNull(categoryDTO.getDeletedAt());
    }

    @Test
    public void editCategory_shouldSucceed() {
        Category category = CategoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Category categoryCpy = CategoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);

        when(repository.findById(category.getId())).thenReturn(Optional.ofNullable(category));
        when(repository.save(isA(Category.class))).thenReturn(category);

        categoryService.editCategory(categoryDTO);

        assertEquals(categoryDTO.getDescription(), categoryCpy.getDescription());
        assertEquals(categoryDTO.getName(), categoryCpy.getName());
        assertEquals(categoryDTO.getCreatedAt(), categoryCpy.getCreatedAt());
    }

    @Test
    public void findById_shouldSucceed() {
        Category categorySaved = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(repository.findById(category.getId())).thenReturn(Optional.ofNullable(category));

        CategoryDTO valueReturned = categoryService.findById(category.getId());

        assertEquals(valueReturned.getDescription(), categorySaved.getDescription());
        assertEquals(valueReturned.getSvg(), categorySaved.getSvg());
        assertEquals(valueReturned.getName(), categorySaved.getName());
        assertEquals(valueReturned.getCreatedAt(), categorySaved.getCreatedAt());
        assertEquals(valueReturned.getModifiedAt(), categorySaved.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void findByIdNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> categoryService.findById(1L)
        );
    }

    @Test
    public void deleteCategory_shouldSucceed() {
        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(repository.findById(category.getId())).thenReturn(Optional.ofNullable(category));

        categoryService.deleteCategory(category.getId());

        verify(repository, times(1)).save(category);
    }

    @Test
    public void deleteCategoryNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> categoryService.deleteCategory(1L)
        );
    }

    @Test
    public void listCategoryWithData_shouldSucceed() {
        Category category1 = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .id(1L)
                .build();

        Category category2 = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        when(repository.findAll()).thenReturn(List.of(category1, category2));

        List<CategoryDTO> valuesReturned = categoryService.listCategory();

        assertEquals(valuesReturned.size(), 2);
    }

    @Test
    public void listCategoryWithNoData_shouldSucceed() {

        when(repository.findAll()).thenReturn(List.of());

        List<CategoryDTO> valuesReturned = categoryService.listCategory();

        assertEquals(valuesReturned.size(), 0);
    }
}
