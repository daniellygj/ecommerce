package com.ecommerce.product.service;

import com.ecommerce.product.model.Brand;

import java.util.List;

public interface BrandService {

    Brand createBrand(Brand brand);

    Brand editBrand(Brand brand);

    void deleteBrand(Long id);

    List<Brand> listBrand();
}
