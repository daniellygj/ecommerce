package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.DiscountDTO;

import java.util.List;

public interface DiscountService {

    DiscountDTO createDiscount(DiscountDTO discount);

    DiscountDTO editDiscount(DiscountDTO discount);

    DiscountDTO findById(Long id);

    void deleteDiscount(Long id);

    List<DiscountDTO> listDiscount();
}
