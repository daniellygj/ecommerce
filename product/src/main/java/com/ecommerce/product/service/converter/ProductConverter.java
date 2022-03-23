package com.ecommerce.product.service.converter;

import com.ecommerce.product.controller.dto.BrandDTO;
import com.ecommerce.product.controller.dto.InventoryDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Brand;
import com.ecommerce.product.model.Inventory;
import com.ecommerce.product.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class ProductConverter {

    private static final ModelMapper mapper = new ModelMapper();


    public static Converter<Product, ProductDTO> convertDto = mappingContext -> {
        Product product = mappingContext.getSource();
        ProductDTO productDTO = mappingContext.getDestination();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setDiscount(product.getDiscount().getId());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setModifiedAt(product.getModifiedAt());
        productDTO.setDeletedAt(product.getDeletedAt());

        InventoryDTO inventoryDTO = mapper.map(product.getInventory(), InventoryDTO.class);
        productDTO.setInventory(inventoryDTO);

        BrandDTO brandDTO = mapper.map(product.getBrand(), BrandDTO.class);
        productDTO.setBrand(brandDTO);

        return productDTO;
    };

    public static Converter<ProductDTO, Product> convert = mappingContext -> {
        Product product = mappingContext.getDestination();
        ProductDTO productDTO = mappingContext.getSource();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.getCategory().setId(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.getDiscount().setId(productDTO.getDiscount());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setModifiedAt(productDTO.getModifiedAt());
        product.setDeletedAt(productDTO.getDeletedAt());

        Inventory inventory = mapper.map(productDTO.getInventory(), Inventory.class);
        product.setInventory(inventory);

        Brand brand = mapper.map(productDTO.getBrand(), Brand.class);
        product.setBrand(brand);

        return product;
    };

    public static void config(ModelMapper mapper) {
        mapper.typeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getCategory().getId(), ProductDTO::setCategory);

        mapper.typeMap(Product.class, ProductDTO.class)
                .addMapping(src -> src.getDiscount().getId(), ProductDTO::setDiscount);
    }
}
