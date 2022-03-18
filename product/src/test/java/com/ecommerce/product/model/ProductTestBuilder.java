package com.ecommerce.product.model;

import com.ecommerce.product.model.Product.ProductBuilder;
import java.time.LocalDateTime;

public class ProductTestBuilder {

    private static final Long ID = 1L;
    private static final String NAME = "Mock Category Name";
    private static final String DESCRIPTION = "Mock Category Description";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);

    public static ProductTestBuilder init() {
        return new ProductTestBuilder();
    }

    public ProductBuilder withDefaultValues() {
        return Product.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }
}
