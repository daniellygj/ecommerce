package com.ecommerce.product.model;

import java.time.LocalDateTime;

import com.ecommerce.product.model.Brand.BrandBuilder;

public class BrandTestBuilder {

    private static final Long ID = 1L;
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);


    private static final String DEFAULT_NAME = "Mock Brand Name" ;
    private static final String DEFAULT_DESCRIPTION = "Mock Brand Description";

    private static final String NEW_NAME = "New Brand Name" ;
    private static final String NEW_DESCRIPTION = "New Brand Description";

    public static BrandTestBuilder init() {
        return new BrandTestBuilder();
    }

    public BrandBuilder withDefaultValues() {
        return Brand.builder()
                .id(ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }

    public BrandBuilder withDefaultValuesNew() {
        return Brand.builder()
                .id(ID)
                .name(NEW_NAME)
                .description(NEW_DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }
}
