package com.ecommerce.product.service.converter;

import com.ecommerce.product.controller.dto.ImageDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Image;
import com.ecommerce.product.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.nio.charset.StandardCharsets;

public class ImageConverter {


    private ModelMapper mapper = new ModelMapper();


    Converter<Image, ImageDTO> convertDTO = mappingContext -> {
        Image image = mappingContext.getSource();
        ImageDTO imageDTO = mappingContext.getDestination();

        imageDTO.setId(image.getId());
        imageDTO.setDescription(image.getDescription());
        imageDTO.setImage(new String(image.getImage(), StandardCharsets.UTF_8));

        ProductDTO productDTO = mapper.map(image.getProduct(), ProductDTO.class);
        imageDTO.setProduct(productDTO);

        return imageDTO;
    };

    Converter<ImageDTO, Image> convert = mappingContext -> {
        Image image = mappingContext.getDestination();
        ImageDTO imageDTO = mappingContext.getSource();

        image.setId(imageDTO.getId());
        image.setDescription(imageDTO.getDescription());
        image.setImage(imageDTO.getImage().getBytes(StandardCharsets.UTF_8));

        Product product = mapper.map(imageDTO.getProduct(), Product.class);
        image.setProduct(product);

        return image;
    };


}
