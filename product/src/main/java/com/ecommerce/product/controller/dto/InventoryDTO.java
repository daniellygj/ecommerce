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
public class InventoryDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "10", description = "The amount of products")
    private int quantity;

//    @Schema(example = "Product object", description = "The products on the inventory") todo
//    private ProductDTO product;
}
