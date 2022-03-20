package com.ecommerce.product.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    @Schema(example= "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example= "e-comm", description = "Brand name")
    private String name;

    @Schema(example= "Online store that sells cool stuff", description = "Simple description of the brand")
    private String description;

}
