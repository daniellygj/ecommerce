package com.ecommerce.product.service;

import com.ecommerce.product.model.*;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.impl.ProductServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private DiscountService discountService;

    private ProductService service;

    private Product product;
    private Product productEdited;
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
        assertEquals(valueReturned.getCategory().getId(), productCpy.getCategory().getId()); // n�o esta comparando certo
        assertEquals(valueReturned.getInventory().getId(), productCpy.getInventory().getId());
        assertEquals(valueReturned.getDiscount().getId(), productCpy.getDiscount().getId());
        assertEquals(valueReturned.getPrice(), productCpy.getPrice());
        assertNotEquals(valueReturned.getCreatedAt(), productCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), productCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void editProduct_shouldSucceed() {
        createProductWithDefaultValues();
        createProductEdited();

        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();

        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();


        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(repository.save(product)).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenReturn(category);
        when(discountService.findById(productEdited.getDiscount().getId())).thenReturn(discount);

        Product valueReturned = service.editProduct(productEdited);

        assertNotEquals(valueReturned.getDescription(), productCpy.getDescription());
        assertNotEquals(valueReturned.getName(), productCpy.getName());
        assertEquals(valueReturned.getId(), productCpy.getId());
        assertNotEquals(valueReturned.getCategory().getId(), productCpy.getCategory().getId());
        assertEquals(valueReturned.getInventory().getId(), productCpy.getInventory().getId());
        assertNotEquals(valueReturned.getDiscount().getId(), productCpy.getDiscount().getId());
        assertNotEquals(valueReturned.getPrice(), productCpy.getPrice());
        assertEquals(valueReturned.getCreatedAt(), productCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), productCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void editProductNonExistent_shouldFail() {
        createProductEdited();
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEdited)
        );
    }

    @Test
    public void editProductWithDiscountNonExixstent_shouldFail() {
        createProductWithDefaultValues();
        createProductEdited();

        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(repository.save(product)).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenReturn(category);
        when(discountService.findById(productEdited.getDiscount().getId())).thenThrow(GenericException.NotFoundException.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEdited)
        );

    }

    @Test
    public void editProductWithCategoryNonExistent_shouldFail() {
        createProductWithDefaultValues();
        createProductEdited();

        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();


        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(repository.save(product)).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenThrow(GenericException.NotFoundException.class);
        when(discountService.findById(productEdited.getDiscount().getId())).thenReturn(discount);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEdited)
        );
    }

    @Test
    public void findById_shouldSucceed() {
        createProductWithDefaultValues();

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        Product valueReturned = service.findById(product.getId());

        assertEquals(valueReturned.getDescription(), productCpy.getDescription());
        assertEquals(valueReturned.getName(), productCpy.getName());
        assertEquals(valueReturned.getId(), productCpy.getId());
        assertEquals(valueReturned.getCategory().getId(), productCpy.getCategory().getId()); // n�o esta comparando certo
        assertEquals(valueReturned.getInventory().getId(), productCpy.getInventory().getId());
        assertEquals(valueReturned.getDiscount().getId(), productCpy.getDiscount().getId());
        assertEquals(valueReturned.getPrice(), productCpy.getPrice());
        assertEquals(valueReturned.getCreatedAt(), productCpy.getCreatedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void findByIdWithProductDeleted_shouldFail() {
        createProductWithDefaultValues();
        product.setDeletedAt(LocalDateTime.now());

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        assertThrows(
                ProductException.ProductDeleted.class,
                () -> service.findById(product.getId())
        );
    }

    @Test
    public void deleteProduct_shouldSucceed() {
        createProductWithDefaultValues();

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        service.deleteProduct(product.getId());

        verify(repository, times(1)).save(product);
    }


    @Test
    public void deleteProductNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.deleteProduct(1L)
        );;
    }

    @Test
    public void deleteProductAlreadyDeleted_shouldFail() {
        createProductWithDefaultValues();
        product.setDeletedAt(LocalDateTime.now());


        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        assertThrows(
                ProductException.ProductDeleted.class,
                () -> service.deleteProduct(1L)
        );;
    }

    @Test

    public void listProductsWithData_shouldSucceed() {
        createProductWithDefaultValues();

        when(repository.findAll()).thenReturn(List.of(product, productCpy));

        List<Product> valuesReturned = service.listProduct();

        assertEquals(valuesReturned.size(), 2);
        assertTrue(valuesReturned.contains(product));
        assertTrue(valuesReturned.contains(productCpy));
    }

    @Test
    public void listProductsWithNoData_shouldSucceed() {
        when(repository.findAll()).thenReturn(List.of());

        List<Product> valuesReturned = service.listProduct();

        assertEquals(valuesReturned.size(), 0);
    }

    private void createProductEdited() {
        Category category = CategoryTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();

        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .id(123L)
                .build();

        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        this.productEdited = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .category(category)
                .discount(discount)
                .description("New Description")
                .name("New Product Name")
                .price(56.8f)
                .build();
    }

    private void createProductWithDefaultValues() {
        this.product = createProduct();
        this.productCpy = createProduct();
    }

    private Product createProduct() {
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

        return ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .category(category)
                .discount(discount)
                .build();
    }

}
