package com.ecommerce.product.service.converter;

import org.modelmapper.ModelMapper;

public class Converter {

    public static ModelMapper init() {
        ModelMapper mapper = new ModelMapper();
        ProductConverter.config(mapper);

        mapper.addConverter(ImageConverter.convertDTO);
        mapper.addConverter(ImageConverter.convert);
        mapper.addConverter(ProductConverter.convert);
        mapper.addConverter(ProductConverter.convertDto);

        return mapper;
    }
}
