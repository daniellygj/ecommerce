package com.ecommerce.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    private float price;

    @OneToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;
}
