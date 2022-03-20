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

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;
}
