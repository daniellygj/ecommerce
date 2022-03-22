package com.ecommerce.product.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "Rainbow socks", description = "A simple description of the image")
    private String description;

    @Schema(example = "data:image/base64", description = "Image encoded to base64")
    private String image;

    @Schema(implementation = ProductDTO.class, description = "A simple description of the image")
    private ProductDTO product;
}
