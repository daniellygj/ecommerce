package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
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
import org.modelmapper.ModelMapper;
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

    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new InventoryServiceImpl(repository, productService);
        this.mapper = new ModelMapper();
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

        when(repository.save(isA(Inventory.class))).thenReturn(inventory);
        InventoryDTO inventoryDTO = mapper.map(inventory, InventoryDTO.class);

        service.createInventory(inventoryDTO);

        assertEquals(inventoryDTO.getQuantity(), inventoryCpy.getQuantity());
        assertNotEquals(inventoryDTO.getCreatedAt(), inventoryCpy.getCreatedAt());
        assertNotEquals(inventoryDTO.getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(inventoryDTO.getDeletedAt());
    }

    @Test
    public void deleteInventory_shouldSucceed() {
        Inventory inventory = InventoryTestBuilder
                .init()
                .withDefaultValuesNew()
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

        List<InventoryDTO> valuesReturned = service.listInventory();

        assertEquals(valuesReturned.size(), 2);
    }

    @Test
    public void listInventoryWithNoData_shouldSucceed() {
        when(repository.findAll()).thenReturn(List.of());

        List<InventoryDTO> valuesReturned = service.listInventory();

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

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);

        when(productService.findById(product.getId())).thenReturn(productDTO);
        when(repository.save(isA(Inventory.class))).thenReturn(inventory);

        service.addItem(product.getId(), 10);

        assertEquals(productDTO.getInventory().getQuantity(), inventoryCpy.getQuantity() + 10);
        assertNotEquals(productDTO.getInventory().getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(productDTO.getInventory().getDeletedAt());
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

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        when(productService.findById(product.getId())).thenReturn(productDTO);
        when(repository.save(isA(Inventory.class))).thenReturn(inventory);

        service.removeItem(product.getId(), 5);

        assertEquals(productDTO.getInventory().getQuantity(), inventoryCpy.getQuantity() - 5);
        assertNotEquals(productDTO.getInventory().getModifiedAt(), inventoryCpy.getModifiedAt());
        assertNull(productDTO.getInventory().getDeletedAt());
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

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        when(productService.findById(product.getId())).thenReturn(productDTO);

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
