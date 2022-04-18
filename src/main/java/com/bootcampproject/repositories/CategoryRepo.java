package com.bootcampproject.repositories;

import com.bootcampproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByName(String name);
    @Query(value = "select * from category where  parent_category_id=?1",nativeQuery = true)
    List<Category> findByParentCategoryId(Long parentCategoryId);
    Long countByParentCategoryId(Long parentCategoryId);
}
