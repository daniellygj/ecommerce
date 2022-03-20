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
public class BrandDTO {
    @Schema(example= "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example= "e-comm", description = "Brand name")
    private String name;

    @Schema(example= "Online store that sells cool stuff", description = "Simple description of the brand")
    private String description;

    @JsonIgnore
    private String teste;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;

}
