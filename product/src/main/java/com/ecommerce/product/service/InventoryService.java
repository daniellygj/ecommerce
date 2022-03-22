package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    void deleteInventory(Long id);

    List<InventoryDTO> listInventory();

    InventoryDTO addItem(Long productId, int quantity);

    InventoryDTO removeItem(Long productId, int quantity);

    InventoryDTO findById(Long id);
}
