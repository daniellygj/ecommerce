package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.DiscountDTO;
import com.ecommerce.product.model.Discount;
import com.ecommerce.product.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;


@RestController
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService service;

    private final ModelMapper mapper;

    public DiscountController(@Autowired DiscountService service) {
        this.service = service;
        this.mapper = new ModelMapper();
    }

    @Operation(summary = "Create a discount", description = "Returns a single discount", tags = { "discount" })
    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discount) {
        Discount discountSaved = service.createDiscount(mapper.map(discount, Discount.class));
        DiscountDTO discountDTO = mapper.map(discountSaved, DiscountDTO.class);

        return ResponseEntity.ok(discountDTO);
    }

    @Operation(summary = "Edit a discount", description = "Returns a single discount", tags = { "discount" })
    @PutMapping
    public ResponseEntity<DiscountDTO> editDiscount(@RequestBody DiscountDTO discount) {
        Discount discountSaved = service.editDiscount(mapper.map(discount, Discount.class));
        DiscountDTO discountDTO = mapper.map(discountSaved, DiscountDTO.class);

        return ResponseEntity.ok(discountDTO);
    }

    @Operation(summary = "Delete a discount", description = "Returns a empty body", tags = { "discount" })
    @DeleteMapping("{id}")
    public void deleteDiscount(@PathVariable("id") Long id) {
        service.deleteDiscount(id);
    }

    @Operation(summary = "List discounts", description = "Returns a list of discounts", tags = { "discount" })
    @GetMapping
    public ResponseEntity<List<DiscountDTO>> listDiscount() {
        List<Discount> discountList = service.listDiscount();
        Type listType = new TypeToken<List<DiscountDTO>>(){}.getType();
        List<DiscountDTO> discountDTOList = mapper.map(discountList, listType);

        return ResponseEntity.ok(discountDTOList);
    }
}
