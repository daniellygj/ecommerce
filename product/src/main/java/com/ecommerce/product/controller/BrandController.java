package com.ecommerce.product.controller;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.model.Brand;
import com.ecommerce.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService service;

    private final ModelMapper mapper;

    public BrandController(@Autowired BrandService service) {
        this.service = service;
        this.mapper = new ModelMapper();
    }

    @Operation(summary = "Register new brand", description = "Returns a single brand", tags = { "brand" })
    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brand) {
        Brand brandSaved = service.createBrand(mapper.map(brand, Brand.class));
        BrandDTO brandDTO = mapper.map(brandSaved, BrandDTO.class);

        return ResponseEntity.ok(brandDTO);
    }

    @Operation(summary = "Edit brand", description = "Returns a single brand", tags = { "brand" })
    @PutMapping
    public ResponseEntity<BrandDTO> editBrand(@RequestBody BrandDTO brand) {
        Brand brandEdited = service.editBrand(mapper.map(brand, Brand.class));
        BrandDTO brandDTO = mapper.map(brandEdited, BrandDTO.class);

        return ResponseEntity.ok(brandDTO);
    }

    @Operation(summary = "Delete brand", description = "Returns a sempty body", tags = { "brand" })
    @DeleteMapping("{id}")
    public void deleteBrand(@PathVariable("id") Long id) {
        service.deleteBrand(id);
    }

    @Operation(summary = "List all brands", description = "Returns a brand list", tags = { "brand" })
    @GetMapping
    public ResponseEntity<List<BrandDTO>> listBrand() {
        List<Brand> brandList = service.listBrand();
        Type listType = new TypeToken<List<BrandDTO>>(){}.getType();
        List<BrandDTO> brandDTOList = mapper.map(brandList, listType);
        return ResponseEntity.ok(brandDTOList);
    }
}
