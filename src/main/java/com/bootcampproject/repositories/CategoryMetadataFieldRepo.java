package com.bootcampproject.repositories;

import com.bootcampproject.entities.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldRepo extends JpaRepository<CategoryMetadataField,Long> {

    CategoryMetadataField findByName(String name);
}
