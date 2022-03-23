package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.ImageDTO;
import com.ecommerce.product.model.Image;
import com.ecommerce.product.repository.ImageRepository;
import com.ecommerce.product.service.ImageService;
import com.ecommerce.product.service.converter.Converter;
import com.ecommerce.product.utils.exception.GenericException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;

    private final ModelMapper mapper;

    public ImageServiceImpl(@Autowired ImageRepository imageRepository) {
        this.repository = imageRepository;
        this.mapper = Converter.init();
    }

    @Override
    public ImageDTO saveImage(Image image) {
        if (image.getId() != null) {
            throw new GenericException.ItemAlreadyExistsException("Image", image.getId());
        }

        Image imageSaved = repository.save(image);
        return mapper.map(imageSaved, ImageDTO.class);
    }

    @Override
    public void deleteImage(Long id) {
        repository.deleteById(id);
    }
}
