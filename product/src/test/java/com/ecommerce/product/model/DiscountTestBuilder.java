package com.ecommerce.product.model;

import com.ecommerce.product.model.Discount.DiscountBuilder;

import java.time.LocalDateTime;

public class DiscountTestBuilder {

    private static final Long ID = 1L;
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);


    private static final String DEFAULT_NAME = "Mock Category Name";
    private static final String DEFAULT_DESCRIPTION = "Mock Category Description";
    private static final float DEFAULT_DISCOUNT_PERCENT = 5.3f;
    private static final boolean DEFAULT_ACTIVE = true;

    private static final String NEW_NAME = "Mock Category Name";
    private static final String NEW_DESCRIPTION = "Mock Category Description";
    private static final float NEW_UNT_PERCENT = 5.3f;
    private static final boolean NEW_ACTIVE = true;

    public static DiscountTestBuilder init() {
        return new DiscountTestBuilder();
    }

    public DiscountBuilder withDefaultValues() {
        return Discount.builder()
                .id(ID)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .discountPercent(DEFAULT_DISCOUNT_PERCENT)
                .active(DEFAULT_ACTIVE);
    }

    public DiscountBuilder withDefaultValuesNew() {
        return Discount.builder()
                .id(ID)
                .name(NEW_NAME)
                .description(NEW_DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .discountPercent(NEW_UNT_PERCENT)
                .active(NEW_ACTIVE);
    }
}
