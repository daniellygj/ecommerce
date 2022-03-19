package com.ecommerce.product.service;

import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.CategoryTestBuilder;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.service.impl.CategoryServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(repository);
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

        when(repository.save(category)).thenReturn(category);

        Category valueReturned = categoryService.createCategory(category);

        assertEquals(valueReturned.getDescription(), categoryCpy.getDescription());
        assertEquals(valueReturned.getName(), categoryCpy.getName());
        assertNotEquals(valueReturned.getCreatedAt(), categoryCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), categoryCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void editCategory_shouldSucceed() {
        Category categorySaved = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Category category = CategoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Category categoryCpy = CategoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        when(repository.findById(categorySaved.getId())).thenReturn(Optional.ofNullable(categorySaved));
        when(repository.save(categorySaved)).thenReturn(categorySaved);

        Category valueReturned = categoryService.editCategory(category);

        assertEquals(valueReturned.getDescription(), categoryCpy.getDescription());
        assertEquals(valueReturned.getName(), categoryCpy.getName());
        assertEquals(valueReturned.getCreatedAt(), categoryCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), categoryCpy.getModifiedAt());
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

        Category valueReturned = categoryService.findById(category.getId());

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

        List<Category> valuesReturned = categoryService.listCategory();

        assertEquals(valuesReturned.size(), 2);
        assertTrue(valuesReturned.contains(category1));
        assertTrue(valuesReturned.contains(category2));
    }

    @Test
    public void listCategoryWithNoData_shouldSucceed() {

        when(repository.findAll()).thenReturn(List.of());

        List<Category> valuesReturned = categoryService.listCategory();

        assertEquals(valuesReturned.size(), 0);
    }
}
