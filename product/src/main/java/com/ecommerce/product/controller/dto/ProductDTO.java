package com.ecommerce.product.controller.dto;

import com.ecommerce.product.model.Brand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Schema(example = "1" , description = "Category Id")
    private Long category;

    @Schema(implementation = InventoryDTO.class, description = "Product inventory")
    private InventoryDTO inventory;

    @Schema(implementation = Brand.class, description = "Product brand")
    private BrandDTO brand;

    @Schema(example = "25,80", description = "Product price")
    private float price;

    @Schema(example = "1", description = "Discount id")
    private Long discount;

    @Schema(implementation = ImageDTO.class, description = "Product photos")
    private List<ImageDTO> images;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;
}
