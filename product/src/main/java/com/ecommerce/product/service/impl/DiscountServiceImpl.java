package com.ecommerce.product.service.impl;

import com.ecommerce.product.model.Discount;
import com.ecommerce.product.repository.DiscountRepository;
import com.ecommerce.product.service.DiscountService;
import com.ecommerce.product.utils.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    public DiscountServiceImpl(@Autowired DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Discount createDiscount(Discount discount) {
        discount.setModifiedAt(LocalDateTime.now());
        discount.setCreatedAt(LocalDateTime.now());
        discount.setActive(true);

        return repository.save(discount);
    }

    @Override
    public Discount editDiscount(Discount discount) {
        Discount discountSaved = repository.findById(discount.getId()).orElseThrow(() -> new GenericException.NotFoundException("Discount", discount.getId()));

        discountSaved.setDiscountPercent(discount.getDiscountPercent());
        discountSaved.setDescription(discount.getDescription());
        discountSaved.setName(discount.getName());
        discountSaved.setActive(discount.isActive());
        discountSaved.setModifiedAt(LocalDateTime.now());

        return repository.save(discountSaved);
    }

    @Override
    public Discount findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Discount", id));
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discountSaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Discount", id));

        discountSaved.setDeletedAt(LocalDateTime.now());
        discountSaved.setModifiedAt(LocalDateTime.now());

        repository.save(discountSaved);
    }

    @Override
    public List<Discount> listDiscount() {
        return repository.findAll();
    }
}
