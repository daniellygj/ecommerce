package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.model.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InventoryService {

    InventoryDTO createInventory(InventoryDTO inventory);

    void deleteInventory(Long id);

    List<InventoryDTO> listInventory();

    InventoryDTO addItem(Long productId, int quantity);

    InventoryDTO removeItem(Long productId, int quantity);
}
