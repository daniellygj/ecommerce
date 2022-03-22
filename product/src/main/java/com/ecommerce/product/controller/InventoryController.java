package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
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

    @Operation(summary = "Delete inventory ", description = "Returns empty body", tags = { "inventory" })
    @DeleteMapping("{id}")
    public void deleteInventory(@PathVariable("id") Long id) {
        service.deleteInventory(id);
    }

    @Operation(summary = "List inventory ", description = "Returns a list of inventory", tags = { "inventory" })
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> listInventory() {
        return ResponseEntity.ok(service.listInventory());
    }

    @Operation(summary = "Add products to an inventory", description = "Returns a single inventory", tags = { "inventory" })
    @PutMapping("/add")
    public ResponseEntity<InventoryDTO> addItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(service.addItem(productId, quantity));
    }

    @Operation(summary = "Remove products to an inventory", description = "Returns a single inventory", tags = { "inventory" })
    @PutMapping("/remove")
    public ResponseEntity<InventoryDTO> removeItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        return ResponseEntity.ok(service.removeItem(productId, quantity));
    }
}
