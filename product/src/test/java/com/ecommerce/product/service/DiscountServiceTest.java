package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.DiscountDTO;
import com.ecommerce.product.model.Discount;
import com.ecommerce.product.model.DiscountTestBuilder;
import com.ecommerce.product.repository.DiscountRepository;
import com.ecommerce.product.service.impl.DiscountServiceImpl;
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
public class DiscountServiceTest {

    @Mock
    private DiscountRepository repository;

    private DiscountService service;

    private ModelMapper mapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new DiscountServiceImpl(repository);
        this.mapper = new ModelMapper();
    }

    @Test
    public void createDiscount_shouldSucceed() {
        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Discount discountCpy = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        DiscountDTO discountDTO = mapper.map(discount, DiscountDTO.class);

        when(repository.save(isA(Discount.class))).thenReturn(discount);

        service.createDiscount(discountDTO);

        assertEquals(discountDTO.getDescription(), discountCpy.getDescription());
        assertEquals(discountDTO.getName(), discountCpy.getName());
        assertEquals(discountDTO.isActive(), discountCpy.isActive());
        assertEquals(discountDTO.getDiscountPercent(), discountCpy.getDiscountPercent());
        assertNotEquals(discountDTO.getCreatedAt(), discountCpy.getCreatedAt());
        assertNotEquals(discountDTO.getModifiedAt(), discountCpy.getModifiedAt());
        assertNull(discountDTO.getDeletedAt());
    }

    @Test
    public void editDiscount_shouldSucceed() {
        Discount discountSaved = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        Discount discountCpy = DiscountTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        DiscountDTO discountDTO = mapper.map(discount, DiscountDTO.class);

        when(repository.findById(discount.getId())).thenReturn(Optional.ofNullable(discountSaved));
        when(repository.save(isA(Discount.class))).thenReturn(discountSaved);

        DiscountDTO valueReturned = service.editDiscount(discountDTO);

        assertEquals(valueReturned.getDiscountPercent(), discountCpy.getDiscountPercent());
        assertEquals(valueReturned.getDescription(), discountCpy.getDescription());
        assertEquals(valueReturned.getName(), discountCpy.getName());
        assertEquals(valueReturned.isActive(), discountCpy.isActive());
        assertNotEquals(valueReturned.getModifiedAt(), discountCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void editDiscountNonExistent_shouldFail() {
        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValuesNew()
                .build();

        DiscountDTO discountDTO = mapper.map(discount, DiscountDTO.class);

        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.editDiscount(discountDTO)
        );
    }

    @Test
    public void findById_shouldSucceed() {
        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Discount discountCpy = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(repository.findById(discount.getId())).thenReturn(Optional.ofNullable(discount));

        DiscountDTO valueReturned = service.findById(discount.getId());

        assertEquals(valueReturned.getDiscountPercent(), discountCpy.getDiscountPercent());
        assertEquals(valueReturned.getDescription(), discountCpy.getDescription());
        assertEquals(valueReturned.getName(), discountCpy.getName());
        assertEquals(valueReturned.isActive(), discountCpy.isActive());
        assertEquals(valueReturned.getCreatedAt(), discountCpy.getCreatedAt());
        assertEquals(valueReturned.getModifiedAt(), discountCpy.getModifiedAt());
        assertNull(valueReturned.getDeletedAt());
    }

    @Test
    public void findByIdNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.findById(1L)
        );
    }

    @Test
    public void deleteDiscount_shouldSucceed() {
        Discount discount = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .build();

        when(repository.findById(discount.getId())).thenReturn(Optional.ofNullable(discount));

        service.deleteDiscount(discount.getId());

        verify(repository, times(1)).save(discount);
    }

    @Test
    public void deleteDiscountNonExistent_shouldFail() {
        assertThrows(
                GenericException.NotFoundException.class,
                () -> service.deleteDiscount(1L)
        );
    }

    @Test
    public void listDiscountWithData_shouldSucceed() {
        Discount discount1 = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .id(1L)
                .build();

        Discount discount2 = DiscountTestBuilder
                .init()
                .withDefaultValues()
                .id(2L)
                .build();

        when(repository.findAll()).thenReturn(List.of(discount1, discount2));

        List<DiscountDTO> valuesReturned = service.listDiscount();

        assertEquals(valuesReturned.size(), 2);
    }

    @Test
    public void listDiscountWithNoData_shouldSucceed() {
        when(repository.findAll()).thenReturn(List.of());

        List<DiscountDTO> valuesReturned = service.listDiscount();

        assertEquals(valuesReturned.size(), 0);
    }
}
