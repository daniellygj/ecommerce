package com.ecommerce.product.service;

import com.ecommerce.product.controller.dto.ImageDTO;
import com.ecommerce.product.model.Image;

public interface ImageService {

    ImageDTO saveImage(Image imageDTO);

    void deleteImage(Long id);
}
