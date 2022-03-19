package com.ecommerce.product.model;

import com.ecommerce.product.model.Inventory.InventoryBuilder;

import java.time.LocalDateTime;

public class InventoryTestBuilder {

    private static final Long ID = 1L;
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.of(2022, 3, 18, 10, 45);

    private static final int DEFAULT_QUANTITY = 26;
    private static final int NEW_QUANTITY = 26;


    public static InventoryTestBuilder init() {
        return new InventoryTestBuilder();
    }

    public InventoryBuilder withDefaultValues() {
        return Inventory.builder()
                .id(ID)
                .quantity(DEFAULT_QUANTITY)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }

    public InventoryBuilder withDefaultValuesNew() {
        return Inventory.builder()
                .id(ID)
                .quantity(NEW_QUANTITY)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT);
    }
}
