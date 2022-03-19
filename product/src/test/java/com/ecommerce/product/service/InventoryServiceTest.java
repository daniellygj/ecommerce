package com.ecommerce.product.service;

import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.model.InventoryTestBuilder;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.ProductTestBuilder;
import com.ecommerce.product.repository.InventoryRepository;
import com.ecommerce.product.service.impl.InventoryServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.InventoryException;
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
public class InventoryServiceTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private ProductService productService;

    private InventoryService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new InventoryServiceImpl(repository, productService);
    }

    @Test
    public void createInventory_shouldSucceed() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Inventory inventoryCpy = InventoryTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(repository.save(inventory)).thenReturn(inventory);

        Inventory valueReturned = service.createInventory(inventory);

        assertEquals(valueReturned.getQuantity(), inventoryCpy.getQuantity());
        assertNotEquals(valueReturned.getCreatedAt(), inventoryCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void deleteInventory_shouldSucceed() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Product product = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .build();

        when(repository.findById(inventory.getId())).thenReturn(Optional.ofNullable(inventory));

        service.deleteInventory(inventory.getId());

        verify(repository, times(1)).save(inventory);
    }

    @Test
    public void deleteInventoryNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.deleteInventory(1L)
        );
    }

    @Test
    public void listInventoryWithData_shouldSucceed() {
        Inventory inventory1 = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .id(1L)
                .build();

        Inventory inventory2 = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .id(2L)
                .build();

        when(repository.findAll()).thenReturn(List.of(inventory1, inventory2));

        List<Inventory> valuesReturned = service.listInventory();

        assertEquals(valuesReturned.size(), 2);
        assertTrue(valuesReturned.contains(inventory1));
        assertTrue(valuesReturned.contains(inventory2));
    }

    @Test
    public void listInventoryWithNoData_shouldSucceed() {
        when(repository.findAll()).thenReturn(List.of());

        List<Inventory> valuesReturned = service.listInventory();

        assertEquals(valuesReturned.size(), 0);
    }

    @Test
    public void addItem_shouldSucceed() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Inventory inventoryCpy = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Product product = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .build();

        when(productService.findById(product.getId())).thenReturn(product);
        when(repository.save(inventory)).thenReturn(inventory);

        Inventory valueReturned = service.addItem(product.getId(), 10);

        assertEquals(valueReturned.getQuantity(), inventoryCpy.getQuantity() + 10);
        assertNotEquals(valueReturned.getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void addItemProductNotFound_shouldFail() {
        when(productService.findById(1L)).thenThrow(GenericException.NotFoundException.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.addItem(1L, 10)
        );
    }

    @Test
    public void removeItem_shouldSucceed() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Inventory inventoryCpy = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Product product = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .build();

        when(productService.findById(product.getId())).thenReturn(product);
        when(repository.save(inventory)).thenReturn(inventory);

        Inventory valueReturned = service.removeItem(product.getId(), 5);

        assertEquals(valueReturned.getQuantity(), inventoryCpy.getQuantity() - 5);
        assertNotEquals(valueReturned.getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void removeItemOutOfStock_shouldFail() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Product product = ProductTestBuilder
                .init()
                .withDefaultValues()
                .inventory(inventory)
                .build();

        when(productService.findById(product.getId())).thenReturn(product);

        assertThrows(
                InventoryException.NotEnoughtStockException.class,
                () -> service.removeItem(product.getId(), inventory.getQuantity() + 10)
        );
    }

    @Test
    public void removeItemProductNotFound_shouldFail() {
        when(productService.findById(1L)).thenThrow(GenericException.NotFoundException.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () ->         service.removeItem(1L, 5)
        );
    }
}
