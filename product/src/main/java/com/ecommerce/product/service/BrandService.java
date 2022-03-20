package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    BrandDTO createBrand(BrandDTO brand);

    BrandDTO editBrand(BrandDTO brand);

    void deleteBrand(Long id);

    List<BrandDTO> listBrand();
}
