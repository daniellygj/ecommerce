package com.ecommerce.product.service;

import com.ecommerce.product.model.Discount;

import java.util.List;

public interface DiscountService {

    Discount createDiscount(Discount discount);

    Discount editDiscount(Discount discount);

    Discount findById(Long id);

    void deleteDiscount(Long id);

    List<Discount> listDiscount();
}
