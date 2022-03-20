package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    private final ModelMapper mapper;

    public InventoryController(@Autowired InventoryService service) {
        this.service = service;
        this.mapper = new ModelMapper();
    }

    @Operation(summary = "Create inventory ", description = "Returns a single inventory", tags = { "inventory" })
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventory) {
        Inventory inventorySaved = service.createInventory(mapper.map(inventory, Inventory.class));
        InventoryDTO inventoryDTO = mapper.map(inventorySaved, InventoryDTO.class);

        return ResponseEntity.ok(inventoryDTO);
    }

    @Operation(summary = "Delete inventory ", description = "Returns empty body", tags = { "inventory" })
    @DeleteMapping("{id}")
    public void deleteInventory(@PathVariable("id") Long id) {
        service.deleteInventory(id);
    }

    @Operation(summary = "List inventory ", description = "Returns a list of inventory", tags = { "inventory" })
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> listInventory() {
        List<Inventory> inventoryList = service.listInventory();
        Type listType = new TypeToken<List<InventoryDTO>>(){}.getType();
        List<InventoryDTO> inventoryDTOList = mapper.map(inventoryList, listType);

        return ResponseEntity.ok(inventoryDTOList);
    }

    @Operation(summary = "Add products to an inventory", description = "Returns a single inventory", tags = { "inventory" })
    @PutMapping("/add")
    public ResponseEntity<InventoryDTO> addItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        Inventory inventory = service.addItem(productId, quantity);
        InventoryDTO inventoryDTO = mapper.map(inventory, InventoryDTO.class);

        return ResponseEntity.ok(inventoryDTO);
    }

    @Operation(summary = "Remove products to an inventory", description = "Returns a single inventory", tags = { "inventory" })
    @PutMapping("/remove")
    public ResponseEntity<InventoryDTO> removeItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        Inventory inventory = service.removeItem(productId, quantity);
        InventoryDTO inventoryDTO = mapper.map(inventory, InventoryDTO.class);

        return ResponseEntity.ok(inventoryDTO);
    }
}
