package com.ecommerce.product.service;

import com.ecommerce.product.model.Image;

public interface ImageService {

    Image saveImage(Image imageDTO);

    void deleteImage(Long id);
}
