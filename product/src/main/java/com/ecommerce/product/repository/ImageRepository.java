package com.ecommerce.product.repository;

import com.ecommerce.product.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
