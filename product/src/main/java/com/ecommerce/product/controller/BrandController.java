package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService service;

    public BrandController(@Autowired BrandService service) {
        this.service = service;
    }

    @Operation(summary = "Register new brand", description = "Returns a single brand", tags = { "brand" })
    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brand) {
        return ResponseEntity.ok(service.createBrand(brand));
    }

    @Operation(summary = "Edit brand", description = "Returns a single brand", tags = { "brand" })
    @PutMapping
    public ResponseEntity<BrandDTO> editBrand(@RequestBody BrandDTO brand) {
        return ResponseEntity.ok(service.editBrand(brand));
    }

    @Operation(summary = "Delete brand", description = "Returns a sempty body", tags = { "brand" })
    @DeleteMapping("{id}")
    public void deleteBrand(@PathVariable("id") Long id) {
        service.deleteBrand(id);
    }

    @Operation(summary = "List all brands", description = "Returns a brand list", tags = { "brand" })
    @GetMapping
    public ResponseEntity<List<BrandDTO>> listBrand() {
        return ResponseEntity.ok(service.listBrand());
    }
}
