package com.ecommerce.product.service.impl;

import com.ecommerce.product.controller.dto.DiscountDTO;
import com.ecommerce.product.controller.dto.ProductDTO;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Discount;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.service.DiscountService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.utils.exception.GenericException;
import com.ecommerce.product.utils.exception.ProductException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final DiscountService discountService;

    private final ModelMapper mapper;

    public ProductServiceImpl(@Autowired ProductRepository repository, @Autowired CategoryService categoryService,
                              @Autowired DiscountService discountService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.discountService = discountService;
        this.mapper = new ModelMapper();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productDTO.setModifiedAt(LocalDateTime.now());
        productDTO.setCreatedAt(LocalDateTime.now());

        Category category = null;
        DiscountDTO discount = null;

        if (productDTO.getCategory() != null) {
            category = categoryService.findById(productDTO.getCategory().getId());
        }

        if (productDTO.getDiscount() != null) {
            discount = discountService.findById(productDTO.getDiscount().getId());
        }

//        productDTO.setCategory(category);
        productDTO.setDiscount(discount);

        Product product = mapper.map(productDTO, Product.class);
        Product productSaved = repository.save(product);

        return mapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public ProductDTO editProduct(ProductDTO productDTO) { // todo - adicionar histórico de preços
        Product productFound = repository.findById(productDTO.getId()).orElseThrow(() -> new GenericException.NotFoundException("Product", productDTO.getId()));

        ProductDTO productDTOToSave = mapper.map(productFound, ProductDTO.class);

        Category category = categoryService.findById(productDTO.getCategory().getId());
        DiscountDTO discountDTO = discountService.findById(productDTO.getDiscount().getId());

        productDTOToSave.setDescription(productDTO.getDescription());
        productDTOToSave.setName(productDTO.getName());
        productDTOToSave.setPrice(productDTO.getPrice());
//        productDTOToSave.setCategory(category);
        productDTOToSave.setDiscount(discountDTO);
        productDTOToSave.setModifiedAt(LocalDateTime.now());

        Product productToSave = mapper.map(productDTOToSave, Product.class);
        repository.save(productToSave);

        return productDTOToSave;
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));
        if (product.getDeletedAt() != null) {
            throw new ProductException.ProductDeleted(id);
        }
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new GenericException.NotFoundException("Product", id));

        if (product.getDeletedAt() != null) {
            throw new ProductException.ProductDeleted(id);
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
