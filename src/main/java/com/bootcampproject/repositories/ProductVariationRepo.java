package com.bootcampproject.repositories;

import com.bootcampproject.entities.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepo extends JpaRepository<ProductVariation,Long> {
}
