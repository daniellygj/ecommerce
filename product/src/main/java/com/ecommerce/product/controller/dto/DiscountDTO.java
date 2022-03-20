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
public class DiscountDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "Winter discount", description = "The name of the discount of the product")
    private String name;

    @Schema(example = "Winter discount for limited time", description = "Simple description of the discount")
    private String description;

    @Schema(example = "13.5", description = "Discount percentage")
    private float discountPercent;

    @Schema(example = "true", description = "Discount active or inactive")
    private boolean active;
}
