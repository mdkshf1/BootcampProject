package com.bootcampproject.repositories;

import com.bootcampproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByName(String name);
}
