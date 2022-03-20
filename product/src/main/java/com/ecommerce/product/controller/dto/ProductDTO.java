package com.ecommerce.product.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "Sound bar", description = "The product name")
    private String name;

    @Schema(example = "A bar that plays sounds", description = "The product name")
    private String description;

    @Schema(example = "Category Object", description = "Product category")
    private CategoryDTO category;

    @Schema(example = "Inventory Object", description = "Product inventory")
    private InventoryDTO inventory;

    @Schema(example = "25,80", description = "Product price")
    private float price;

    @Schema(example = "Discount Object", description = "Product discount")
    private DiscountDTO discount;

}
