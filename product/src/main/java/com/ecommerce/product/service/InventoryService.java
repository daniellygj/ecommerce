package com.ecommerce.product.service;

import com.ecommerce.product.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    void deleteInventory(Long id);

    List<Inventory> listInventory();

    Inventory addItem(Long productId, int quantity);

    Inventory removeItem(Long productId, int quantity);
}
