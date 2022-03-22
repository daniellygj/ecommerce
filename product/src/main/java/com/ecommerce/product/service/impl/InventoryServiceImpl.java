package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.repository.InventoryRepository;
import com.ecommerce.product.service.InventoryService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.service.converter.Converter;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.InventoryException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final ProductService productService;

    private final ModelMapper mapper;

    public InventoryServiceImpl(@Autowired InventoryRepository repository, @Autowired @Lazy ProductService productService) {
        this.repository = repository;
        this.productService = productService;
        this.mapper = Converter.init();
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

        InventoryDTO inventoryDTO = findById(product.getInventory().getId());
        inventoryDTO.setQuantity(inventoryDTO.getQuantity() + quantity);
        inventoryDTO.setModifiedAt(LocalDateTime.now());

        Inventory inventoryToSave = mapper.map(inventoryDTO, Inventory.class);
        Inventory inventorySaved = repository.save(inventoryToSave);

        return mapper.map(inventorySaved, InventoryDTO.class);
    }

    @Override
    public InventoryDTO findById(Long id) {
        Inventory inventory = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Inventory", id));

        if (inventory.getDeletedAt() != null) {
            throw new GenericException.ItemDeletedException("Inventory", id);
        }

        return mapper.map(inventory, InventoryDTO.class);
    }

    @Override
    public InventoryDTO removeItem(Long productId, int quantity) {
        ProductDTO product = productService.findById(productId);

        InventoryDTO inventoryDTO = findById(product.getInventory().getId());

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
