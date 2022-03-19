package com.ecommerce.product.controller;

import com.ecommerce.product.model.Brand;
import com.ecommerce.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private BrandService service;

    public BrandController(@Autowired BrandService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(service.createBrand(brand));
    }

    @PutMapping
    public ResponseEntity<Brand> editBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(service.editBrand(brand));
    }

    @DeleteMapping
    public void deleteBrand(@RequestParam("id") Long id) {
        service.deleteBrand(id);
    }

    @GetMapping
    public ResponseEntity<List<Brand>> listBrand() {
        return ResponseEntity.ok(service.listBrand());
    }
}
