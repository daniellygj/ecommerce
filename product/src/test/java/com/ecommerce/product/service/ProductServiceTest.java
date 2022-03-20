package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.*;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.impl.ProductServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
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

    private ModelMapper mapper;

    private Product product;
    private Product productEdited;
    private Product productCpy;

    private ProductDTO productDTO;
    private ProductDTO productEditedDTO;
    private ProductDTO productCpyDTO;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ProductServiceImpl(repository, categoryService, discountService);
        this.mapper = new ModelMapper();
    }

    @Test
    public void createProduct_shouldSucceed() {
        createProductWithDefaultValues();

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);

        when(repository.save(isA(Product.class))).thenReturn(product);
        when(categoryService.findById(product.getCategory().getId())).thenReturn(product.getCategory());
        when(discountService.findById(product.getDiscount().getId())).thenReturn(productDTO.getDiscount());

        ProductDTO valueReturned = service.createProduct(productDTO);

        assertEquals(valueReturned.getDescription(), productCpy.getDescription());
        assertEquals(valueReturned.getName(), productCpy.getName());
        assertEquals(valueReturned.getId(), productCpy.getId());
        assertEquals(valueReturned.getCategory().getId(), productCpy.getCategory().getId());
        assertEquals(valueReturned.getInventory().getId(), productCpy.getInventory().getId());
        assertEquals(valueReturned.getDiscount().getId(), productCpy.getDiscount().getId());
        assertEquals(valueReturned.getPrice(), productCpy.getPrice());
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

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(repository.save(eq(product))).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenReturn(category);
        when(discountService.findById(productEdited.getDiscount().getId())).thenReturn(productDTO.getDiscount());

        service.editProduct(productEditedDTO);

        assertNotEquals(productEditedDTO.getDescription(), productCpy.getDescription());
        assertNotEquals(productEditedDTO.getName(), productCpy.getName());
        assertEquals(productEditedDTO.getId(), productCpy.getId());
        assertNotEquals(productEditedDTO.getCategory().getId(), productCpy.getCategory().getId());
        assertEquals(productEditedDTO.getInventory().getId(), productCpy.getInventory().getId());
        assertNotEquals(productEditedDTO.getDiscount().getId(), productCpy.getDiscount().getId());
        assertNotEquals(productEditedDTO.getPrice(), productCpy.getPrice());
        assertEquals(productEditedDTO.getCreatedAt(), productCpy.getCreatedAt());
        assertNull(productEditedDTO.getDeletedAt());
    }

    @Test
    public void editProductNonExistent_shouldFail() {
        createProductEdited();
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEditedDTO)
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
        when(repository.save(eq(product))).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenReturn(category);
        when(discountService.findById(productEdited.getDiscount().getId())).thenThrow(GenericException.NotFoundException.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEditedDTO)
        );

    }

    @Test
    public void editProductWithCategoryNonExistent_shouldFail() {
        createProductWithDefaultValues();
        createProductEdited();


        ProductDTO productDTO = mapper.map(product, ProductDTO.class);

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(repository.save(eq(product))).thenReturn(product);
        when(categoryService.findById(productEdited.getCategory().getId())).thenThrow(GenericException.NotFoundException.class);
        when(discountService.findById(productEdited.getDiscount().getId())).thenReturn(productDTO.getDiscount());

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editProduct(productEditedDTO)
        );
    }

    @Test
    public void findById_shouldSucceed() {
        createProductWithDefaultValues();

        when(repository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        ProductDTO valueReturned = service.findById(product.getId());

        assertEquals(valueReturned.getDescription(), productCpy.getDescription());
        assertEquals(valueReturned.getName(), productCpy.getName());
        assertEquals(valueReturned.getId(), productCpy.getId());
        assertEquals(valueReturned.getCategory().getId(), productCpy.getCategory().getId());
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

        List<ProductDTO> valuesReturned = service.listProduct();

        assertEquals(valuesReturned.size(), 2);
    }

    @Test
    public void listProductsWithNoData_shouldSucceed() {
        when(repository.findAll()).thenReturn(List.of());

        List<ProductDTO> valuesReturned = service.listProduct();

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

        this.productEditedDTO = mapper.map(this.productEdited, ProductDTO.class);
    }

    private void createProductWithDefaultValues() {
        this.product = createProduct();
        this.productCpy = createProduct();

        this.productDTO = mapper.map(this.product, ProductDTO.class);
        this.productCpyDTO = mapper.map(this.productCpy, ProductDTO.class);
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
