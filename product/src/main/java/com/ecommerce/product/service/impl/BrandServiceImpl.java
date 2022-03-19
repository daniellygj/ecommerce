package com.ecommerce.product.service.impl;

import com.ecommerce.product.model.Brand;
import com.ecommerce.product.repository.BrandRepository;
import com.ecommerce.product.service.BrandService;
import com.ecommerce.product.utils.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;

    public BrandServiceImpl(@Autowired BrandRepository brandRepository) {
        this.repository = brandRepository;
    }

    // todo - o nome deve ser único?
    public Brand createBrand(Brand brand) {
        brand.setModifiedAt(LocalDateTime.now());
        brand.setCreatedAt(LocalDateTime.now());

        return repository.save(brand);
    }

    public Brand editBrand(Brand brand) {
        Brand brandSaved = repository.findById(brand.getId()).orElseThrow(() -> new GenericException.NotFoundException("Brand", brand.getId()));

        brandSaved.setDescription(brand.getDescription());
        brandSaved.setName(brand.getName());
        brandSaved.setModifiedAt(LocalDateTime.now());

        return repository.save(brandSaved);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brandSaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Brand", id));

        brandSaved.setDeletedAt(LocalDateTime.now());

        repository.save(brandSaved);
    }

    @Override
    public List<Brand> listBrand() {
        return repository.findAll();
    }
}
