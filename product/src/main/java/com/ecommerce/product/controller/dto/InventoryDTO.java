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
public class InventoryDTO {

    @Schema(example = "0", description = "You don't need to fill this =)")
    private Long id;

    @Schema(example = "10", description = "The amount of products")
    private int quantity;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;

}
