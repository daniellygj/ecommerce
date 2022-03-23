package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.CategoryDTO;
import com.ecommerce.product.controller.dto.DiscountDTO;
import com.ecommerce.product.controller.dto.ImageDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.*;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.*;
import com.ecommerce.product.service.converter.Converter;
import com.ecommerce.product.service.converter.ImageConverter;
import com.ecommerce.product.utils.exception.GenericException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final DiscountService discountService;
    private final ImageService imageService;

    private final ModelMapper mapper;

    public ProductServiceImpl(@Autowired ProductRepository repository, @Autowired CategoryService categoryService, @Autowired DiscountService discountService, @Autowired ImageService imageService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.discountService = discountService;
        this.imageService = imageService;
        this.mapper = Converter.init();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productDTO.setModifiedAt(LocalDateTime.now());
        productDTO.setCreatedAt(LocalDateTime.now());

        Category category = null;
        Discount discount = null;
        Inventory inventory = new Inventory();

        if (productDTO.getCategory() != null) {
            category = mapper.map(categoryService.findById(productDTO.getCategory()), Category.class);
        }

        if (productDTO.getDiscount() != null) {
            discount = mapper.map(discountService.findById(productDTO.getDiscount()), Discount.class);
        }

        Product product = mapper.map(productDTO, Product.class);

        product.setCategory(category);
        product.setDiscount(discount);
        product.setInventory(inventory);

        Product productSaved = repository.save(product);
        List<ImageDTO> imageDTOList = new ArrayList<>();

        productDTO.getImages().forEach(img -> {
            img.setProduct(productDTO);
            Image image = mapper.map(img, Image.class);
            image.setProduct(product);
            image.setImage(img.getImage().getBytes(StandardCharsets.UTF_8));
            ImageDTO imageSaved = imageService.saveImage(image);

            imageDTOList.add(imageSaved);
        });

        ProductDTO productDTOSaved = mapper.map(productSaved, ProductDTO.class);
        productDTOSaved.setImages(imageDTOList);

        return productDTOSaved;
    }

    @Override
    public ProductDTO editProduct(ProductDTO productDTO) { // todo - adicionar histórico de preços
        Product productFound = repository.findById(productDTO.getId()).orElseThrow(() -> new GenericException.NotFoundException("Product", productDTO.getId()));

        CategoryDTO categoryDTO = categoryService.findById(productDTO.getCategory());
        DiscountDTO discountDTO = discountService.findById(productDTO.getDiscount());
        Category category = mapper.map(categoryDTO, Category.class);
        Discount discount = mapper.map(discountDTO, Discount.class);

        productFound.setDescription(productDTO.getDescription());
        productFound.setName(productDTO.getName());
        productFound.setPrice(productDTO.getPrice());
        productFound.setCategory(category);
        productFound.setDiscount(discount);
        productFound.setModifiedAt(LocalDateTime.now());

        Product productSaved = repository.save(productFound);

        return mapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));
        if (product.getDeletedAt() != null) {
            throw new GenericException.ItemDeletedException("Product", id);
        }
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));

        if (product.getDeletedAt() != null) {
            throw new GenericException.ItemDeletedException("Product", id);
        }

        product.setDeletedAt(LocalDateTime.now());
        product.setModifiedAt(LocalDateTime.now());

        repository.save(product);
    }

    @Override
    public List<ProductDTO> listProduct() {
        List<Product> productList = repository.findAll();
        Type listType = new TypeToken<List<ProductDTO>>(){}.getType();
        List<ProductDTO> productDTOList = mapper.map(productList, listType);
        return productDTOList;
    }
}
