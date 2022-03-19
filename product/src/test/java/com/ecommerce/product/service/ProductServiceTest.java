package com.ecommerce.product.service;

import com.ecommerce.product.model.*;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryService categoryService;

    @Mock
    DiscountService discountService;

    private ProductService service;

    private Product product;
    private Product productCpy;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ProductServiceImpl(repository, categoryService, discountService);
    }

    @Test
    public void createProduct_shouldSucceed() {
        createProductWithDefaultValues();

        when(repository.save(product)).thenReturn(product);

        Product valueReturned = service.createProduct(product);

        assertEquals(valueReturned.getDescription(), productCpy.getDescription());
        assertEquals(valueReturned.getName(), productCpy.getName());
        assertEquals(valueReturned.getId(), productCpy.getId());
        assertEquals(valueReturned.getCategory(), productCpy.getCategory()); // não esta comparando certo
        assertEquals(valueReturned.getInventory(), productCpy.getInventory());
        assertEquals(valueReturned.getDiscount(), productCpy.getDiscount());
        assertEquals(valueReturned.getPrice(), productCpy.getPrice());
        assertNotEquals(valueReturned.getCreatedAt(), productCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), productCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    private void createProductWithDefaultValues() {
        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();


        this.product = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .category(category)
                .discount(discount)
                .build();

        Category categoryCpy = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Discount discountCpy = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Inventory inventoryCpy = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        this.productCpy = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventoryCpy)
                .category(categoryCpy)
                .discount(discountCpy)
                .build();
    }
}
