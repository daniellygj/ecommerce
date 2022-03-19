package com.ecommerce.product.controller;

import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;


    public InventoryController(@Autowired InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(service.createInventory(inventory));
    }

    @DeleteMapping("{id}")
    public void deleteInventory(@PathVariable("id") Long id) {
        service.deleteInventory(id);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> listInventory() {
        return ResponseEntity.ok(service.listInventory());
    }

    @PutMapping("/add")
    public ResponseEntity<Inventory> addItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(service.addItem(productId, quantity));
    }

    @PutMapping("/remove")
    public ResponseEntity<Inventory> removeItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(service.removeItem(productId, quantity));
    }
}
