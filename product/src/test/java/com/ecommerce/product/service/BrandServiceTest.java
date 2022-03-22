package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.model.Brand;
import com.ecommerce.product.model.BrandTestBuilder;
import com.ecommerce.product.repository.BrandRepository;
import com.ecommerce.product.service.converter.Converter;
import com.ecommerce.product.service.impl.BrandServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    private BrandService brandService;

    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        brandService = new BrandServiceImpl(brandRepository);
        this.mapper = Converter.init();
    }

    @Test
    public void createBrand_shouldSucceed() {
        Brand brand = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Brand brandCpy = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(brandRepository.save(isA(Brand.class))).thenReturn(brand);

        BrandDTO brandDTO = mapper.map(brand, BrandDTO.class);

        brandService.createBrand(brandDTO);

        assertEquals(brandDTO.getDescription(), brandCpy.getDescription());
        assertEquals(brandDTO.getName(), brandCpy.getName());
        assertNotEquals(brandDTO.getCreatedAt(), brandCpy.getCreatedAt());
        assertNotEquals(brandDTO.getModifiedAt(), brandCpy.getModifiedAt());
        assertNull(brandDTO.getDeletedAt());
    }

    @Test
    public void editBrand_shouldSucceed() {
        Brand brand = BrandTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Brand brandCpy = BrandTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        BrandDTO brandDTO = mapper.map(brand, BrandDTO.class);

        when(brandRepository.findById(brand.getId())).thenReturn(Optional.ofNullable(brand));
        when(brandRepository.save(isA(Brand.class))).thenReturn(brand);

        brandService.editBrand(brandDTO);

        assertEquals(brandDTO.getDescription(), brandCpy.getDescription());
        assertEquals(brandDTO.getName(), brandCpy.getName());
    }

    @Test
    public void editBrandNonExistent_shouldFail() {
        Brand brand = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        BrandDTO brandDTO = mapper.map(brand, BrandDTO.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> brandService.editBrand(brandDTO)
        );
    }

    @Test
    public void deleteBrand_shouldSucceed() {
        Brand brandSaved = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(brandRepository.findById(brandSaved.getId())).thenReturn(Optional.ofNullable(brandSaved));
        when(brandRepository.save(brandSaved)).thenReturn(brandSaved);

        brandService.deleteBrand(brandSaved.getId());

        verify(brandRepository, times(1)).save(brandSaved);
    }

    @Test
    public void deleteBrandNonExistent_shouldFail() {

        assertThrows(
                GenericException.NotFoundException.class,
                () -> brandService.deleteBrand(1L)
        );
    }

    @Test
    public void listBrandWithValues_shouldSucceed() {
        Brand brand1 = BrandTestBuilder
                .init()
                .withDefaultValues()
                .id(1L)
                .build();

        Brand brand2 = BrandTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        when(brandRepository.findAll()).thenReturn(List.of(brand1, brand2));


        List<BrandDTO> valuesReturned = brandService.listBrand();

        assertEquals(valuesReturned.size(), 2);
    }

    @Test
    public void listBrandWithNoValues_shouldSucceed() {
        when(brandRepository.findAll()).thenReturn(List.of());

        List<BrandDTO> valuesReturned = brandService.listBrand();

        assertEquals(valuesReturned.size(), 0);
    }
}
