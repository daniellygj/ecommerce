package com.ecommerce.product.model;

import java.time.LocalDateTime;
import com.ecommerce.product.model.Discount.DiscountBuilder;

public class DiscountTestBuilder {

    private static final Long ID = 1L;
    private static final String NAME = "Mock Category Name";
    private static final String DESCRIPTION = "Mock Category Description";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final float DISCOUNT_PERCENT = 5.3f;
    private static final boolean ACTIVE = true;



    public DiscountBuilder withDefaultValues() {
        return Discount.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .discountPercent(DISCOUNT_PERCENT)
                .active(ACTIVE);
    }
}
