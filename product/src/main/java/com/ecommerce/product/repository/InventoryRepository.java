package com.ecommerce.product.repository;

import com.ecommerce.product.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
}
