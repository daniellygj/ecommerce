package com.ecommerce.product.controller;

import com.ecommerce.product.model.Discount;
import com.ecommerce.product.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService service;

    public DiscountController(@Autowired DiscountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        return ResponseEntity.ok(service.createDiscount(discount));
    }

    @PutMapping
    public ResponseEntity<Discount> editDiscount(@RequestBody Discount discount) {
        return ResponseEntity.ok(service.editDiscount(discount));
    }

    @DeleteMapping("{id}")
    public void deleteDiscount(@PathVariable("id") Long id) {
        service.deleteDiscount(id);
    }

    @GetMapping
    public ResponseEntity<List<Discount>> listDiscount() {
        return ResponseEntity.ok(service.listDiscount());
    }

}
