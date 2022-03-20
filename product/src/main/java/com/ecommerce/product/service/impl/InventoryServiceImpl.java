package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.repository.InventoryRepository;
import com.ecommerce.product.service.InventoryService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.InventoryException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final ProductService productService;

    private final ModelMapper mapper;

    public InventoryServiceImpl(@Autowired InventoryRepository repository, @Autowired ProductService productService) {
        this.repository = repository;
        this.productService = productService;
        this.mapper = new ModelMapper();
    }

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        inventoryDTO.setModifiedAt(LocalDateTime.now());
        inventoryDTO.setCreatedAt(LocalDateTime.now());

        Inventory inventory = mapper.map(inventoryDTO, Inventory.class);
        Inventory inventorySaved = repository.save(inventory);

        return mapper.map(inventorySaved, InventoryDTO.class);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventorySaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Inventory", id));

        inventorySaved.setDeletedAt(LocalDateTime.now());

        repository.save(inventorySaved);
    }

    @Override
    public List<InventoryDTO> listInventory() {
        List<Inventory> inventoryList = repository.findAll();
        Type listType = new TypeToken<List<InventoryDTO>>(){}.getType();
        List<InventoryDTO> inventoryDTOList = mapper.map(inventoryList, listType);
        return inventoryDTOList;
    }

    @Override
    public InventoryDTO addItem(Long productId, int quantity) {
        ProductDTO product = productService.findById(productId);

        InventoryDTO inventoryDTO = product.getInventory();
        inventoryDTO.setQuantity(inventoryDTO.getQuantity() + quantity);
        inventoryDTO.setModifiedAt(LocalDateTime.now());

        Inventory inventoryToSave = mapper.map(inventoryDTO, Inventory.class);
        Inventory inventorySaved = repository.save(inventoryToSave);

        return mapper.map(inventorySaved, InventoryDTO.class);
    }

    @Override
    public InventoryDTO removeItem(Long productId, int quantity) {
        ProductDTO product = productService.findById(productId);

        InventoryDTO inventoryDTO = product.getInventory();

        int qty = inventoryDTO.getQuantity() - quantity;

        if (qty < 0) {
            throw new InventoryException.NotEnoughtStockException(productId, quantity);
        }

        inventoryDTO.setQuantity(inventoryDTO.getQuantity() - quantity);
        inventoryDTO.setModifiedAt(LocalDateTime.now());

        Inventory inventoryToSave = mapper.map(inventoryDTO, Inventory.class);
        Inventory inventory = repository.save(inventoryToSave);

        return mapper.map(inventory, InventoryDTO.class);
    }
}
