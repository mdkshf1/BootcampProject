package com.bootcampproject.dto;

import com.bootcampproject.entities.Category;
import lombok.Data;

@Data
public class CategoryResponseTO {


    private Long id;
    private String name;
    private CategoryResponseTO parentCategory;
    private CategoryResponseTO childCategory;

    public static CategoryResponseTO mapper(Category category)
    {
        CategoryResponseTO responseTO = new CategoryResponseTO();
        responseTO.setId(category.getId());
        responseTO.setName(category.getName());
        if (category.getParentCategory() != null) {
            responseTO.setParentCategory(mapper(category.getParentCategory()));
        }
        return responseTO;
    }

}
