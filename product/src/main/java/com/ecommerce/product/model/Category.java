package com.ecommerce.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "product_category")
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "svg_path")
    private String svg;

    private LocalDateTime createdAt;
    
    private LocalDateTime modifiedAt;
    
    private LocalDateTime deletedAt;
}
