package com.ecommerce.product.service.impl;

import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.InventoryRepository;
import com.ecommerce.product.service.InventoryService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.utils.exception.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private final ProductService productService;

    public InventoryServiceImpl(@Autowired InventoryRepository repository, @Autowired ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        inventory.setModifiedAt(LocalDateTime.now());
        inventory.setCreatedAt(LocalDateTime.now());

        return repository.save(inventory);
    }

    @Override
    public Inventory editInventory(Inventory inventory) { // todo

        return null;
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventorySaved = repository.findById(id).orElseThrow(() -> new Exception.NotFoundException("Discount", id));

        inventorySaved.setDeletedAt(LocalDateTime.now());

        repository.save(inventorySaved);
    }

    @Override
    public List<Inventory> listInventory() {
        return repository.findAll();
    }

    @Override
    public Inventory addItem(Long productId, int quantity) {
        Product product = productService.findById(productId);

        Inventory inventory = product.getInventory();
        inventory.setQuantity(inventory.getQuantity() + quantity);

        return repository.save(inventory);
    }

    @Override
    public Inventory removeItem(Long productId, int quantity) {
        Product product = productService.findById(productId);

        Inventory inventory = product.getInventory();
        inventory.setQuantity(inventory.getQuantity() - quantity);

        return repository.save(inventory);
    }
}
