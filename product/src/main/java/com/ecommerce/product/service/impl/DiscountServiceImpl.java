package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.DiscountDTO;
import com.ecommerce.product.model.Discount;
import com.ecommerce.product.repository.DiscountRepository;
import com.ecommerce.product.service.DiscountService;
import com.ecommerce.product.utils.exception.GenericException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    private final ModelMapper mapper;

    public DiscountServiceImpl(@Autowired DiscountRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    @Override
    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        discountDTO.setModifiedAt(LocalDateTime.now());
        discountDTO.setCreatedAt(LocalDateTime.now());
        discountDTO.setActive(true);

        Discount discount = mapper.map(discountDTO, Discount.class);
        Discount discountSaved = repository.save(discount);

        return mapper.map(discountSaved, DiscountDTO.class);
    }

    @Override
    public DiscountDTO editDiscount(DiscountDTO discountDTO) {
        Discount discountFound = repository.findById(discountDTO.getId()).orElseThrow(() -> new GenericException.NotFoundException("Discount", discountDTO.getId()));

        discountFound.setDiscountPercent(discountDTO.getDiscountPercent());
        discountFound.setDescription(discountDTO.getDescription());
        discountFound.setName(discountDTO.getName());
        discountFound.setActive(discountDTO.isActive());
        discountFound.setModifiedAt(LocalDateTime.now());

        Discount discountSaved = repository.save(discountFound);

        return mapper.map(discountSaved, DiscountDTO.class);
    }

    @Override
    public DiscountDTO findById(Long id) {
        Discount discount = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Discount", id));

        return mapper.map(discount, DiscountDTO.class);
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discountSaved = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Discount", id));

        discountSaved.setDeletedAt(LocalDateTime.now());
        discountSaved.setModifiedAt(LocalDateTime.now());

        repository.save(discountSaved);
    }

    @Override
    public List<DiscountDTO> listDiscount() {
        List<Discount> discountList = repository.findAll();
        Type listType = new TypeToken<List<DiscountDTO>>(){}.getType();
        List<DiscountDTO> discountDTOList = mapper.map(discountList, listType);
        return discountDTOList;
    }
}
