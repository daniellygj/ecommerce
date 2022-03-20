package com.ecommerce.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "Sound bar", description = "The product name")
    private String name;

    @Schema(example = "A bar that plays sounds", description = "The product name")
    private String description;

    @Schema(implementation = CategoryDTO.class , description = "Product category")
    private CategoryDTO category;

    @Schema(implementation = InventoryDTO.class, description = "Product inventory")
    private InventoryDTO inventory;

    @Schema(example = "25,80", description = "Product price")
    private float price;

    @Schema(implementation = DiscountDTO.class, description = "Product discount")
    private DiscountDTO discount;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;
}
