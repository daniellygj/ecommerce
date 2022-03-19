package com.ecommerce.product.service;

import com.ecommerce.product.model.Brand;
import com.ecommerce.product.model.BrandTestBuilder;
import com.ecommerce.product.repository.BrandRepository;
import com.ecommerce.product.service.impl.BrandServiceImpl;
import com.ecommerce.product.utils.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        brandService = new BrandServiceImpl(brandRepository);
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

        when(brandRepository.save(brand)).thenReturn(brand);

        Brand valueReturned = brandService.createBrand(brand);

        assertEquals(valueReturned.getDescription(), brandCpy.getDescription());
        assertEquals(valueReturned.getName(), brandCpy.getName());
        assertNotEquals(valueReturned.getCreatedAt(), brandCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), brandCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void editBrand_shouldSucceed() {
        Brand brandSaved = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Brand brand = BrandTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Brand brandCpy = BrandTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        when(brandRepository.findById(brandSaved.getId())).thenReturn(Optional.ofNullable(brandSaved));
        when(brandRepository.save(brandSaved)).thenReturn(brandSaved);

        Brand valueReturned = brandService.editBrand(brand);

        assertEquals(valueReturned.getDescription(), brandCpy.getDescription());
        assertEquals(valueReturned.getName(), brandCpy.getName());
        assertEquals(valueReturned.getCreatedAt(), brandCpy.getCreatedAt());
        assertNotEquals(valueReturned.getModifiedAt(), brandCpy.getModifiedAt());
    }

    @Test
    public void editBrandNonExistent_shouldFail() {
        Brand brand = BrandTestBuilder
                .init()
                .withDefaultValues()
                .build();

        assertThrows(
                GenericException.NotFoundException.class,
                () -> brandService.editBrand(brand)
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

        List<Brand> valuesReturned = brandService.listBrand();

        assertEquals(valuesReturned.size(), 2);
        assertTrue(valuesReturned.contains(brand1));
        assertTrue(valuesReturned.contains(brand2));
    }

    @Test
    public void listBrandWithNoValues_shouldSucceed() {
        when(brandRepository.findAll()).thenReturn(List.of());

        List<Brand> valuesReturned = brandService.listBrand();

        assertEquals(valuesReturned.size(), 0);
    }
}
