package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.model.Brand;
import com.ecommerce.product.repository.BrandRepository;
import com.ecommerce.product.service.BrandService;
import com.ecommerce.product.service.converter.Converter;
import com.ecommerce.product.utils.exception.GenericException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;

    private final ModelMapper mapper;

    public BrandServiceImpl(@Autowired BrandRepository brandRepository) {
        this.repository = brandRepository;
        this.mapper = Converter.init();
    }

    public BrandDTO createBrand(BrandDTO brandDTO) {
        brandDTO.setModifiedAt(LocalDateTime.now());
        brandDTO.setCreatedAt(LocalDateTime.now());

        Brand brandToSave = mapper.map(brandDTO, Brand.class);
        Brand brandSaved = repository.save(brandToSave);

        return mapper.map(brandSaved, BrandDTO.class);
    }

    public BrandDTO editBrand(BrandDTO brandDTO) {
        Brand brandFound = repository.findById(brandDTO.getId()).orElseThrow(() -> new GenericException.NotFoundException("Brand", brandDTO.getId()));

        brandFound.setDescription(brandDTO.getDescription());
        brandFound.setName(brandDTO.getName());
        brandFound.setModifiedAt(LocalDateTime.now());

        Brand brandToSave = mapper.map(brandDTO, Brand.class);
        Brand brandSaved = repository.save(brandToSave);

        return mapper.map(brandSaved, BrandDTO.class);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brandSaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Brand", id));

        brandSaved.setDeletedAt(LocalDateTime.now());

        repository.save(brandSaved);
    }

    @Override
    public List<BrandDTO> listBrand() {
        List<Brand> brandList = repository.findAll();
        Type listType = new TypeToken<List<BrandDTO>>(){}.getType();
        List<BrandDTO> brandDTOList = mapper.map(brandList, listType);
        return brandDTOList;

    }
}
