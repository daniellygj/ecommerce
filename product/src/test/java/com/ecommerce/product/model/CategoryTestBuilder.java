package com.ecommerce.product.model;

import com.ecommerce.product.model.Category.CategoryBuilder;

import java.time.LocalDateTime;

public class CategoryTestBuilder {

    private static final Long ID = 1L;
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);

    private static final String DEFAULT_NAME = "Mock Category Name";
    private static final String DEFAULT_DESCRIPTION = "Mock Category Description";
    private static final String DEFAULT_SVG = "svg/path";

    private static final String NEW_NAME = "Mock Category Name";
    private static final String NEW_DESCRIPTION = "Mock Category Description";
    private static final String NEW_SVG = "new/svg/path";

    public static CategoryTestBuilder init() {
        return new CategoryTestBuilder();
    }

    public CategoryBuilder withDefaultValues() {
        return Category.builder()
                .id(ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .svg(DEFAULT_SVG)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }

    public CategoryBuilder withDefaultValuesNew() {
        return Category.builder()
                .id(ID)
                .name(NEW_NAME)
                .description(NEW_DESCRIPTION)
                .svg(NEW_SVG)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }

}
