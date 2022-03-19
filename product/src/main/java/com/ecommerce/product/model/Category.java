package com.ecommerce.product.model;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;
    
    private LocalDateTime modifiedAt;
    
    private LocalDateTime deletedAt;

    @Column(name = "svg_path")
    private String svg;
}
