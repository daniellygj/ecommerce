package com.ecommerce.product.model;

import com.ecommerce.product.model.Category.CategoryBuilder;

import java.time.LocalDateTime;

public class CategoryTestBuilder {

    private static final Long ID = 1L;
    private static final String NAME = "Mock Category Name";
    private static final String DESCRIPTION = "Mock Category Description";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime DELETED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);

    public static CategoryTestBuilder init() {
        return new CategoryTestBuilder();
    }

    public CategoryBuilder withDefaultValues() {
        return Category.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }

    public CategoryBuilder withDefaultValues_plusDeletedAt() {
        return Category.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .deletedAt(DELETED_AT);
    }
}
