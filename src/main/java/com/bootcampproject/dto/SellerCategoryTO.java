package com.bootcampproject.dto;

import com.bootcampproject.entities.Category;
import lombok.Data;

import javax.persistence.*;

@Data
public class SellerCategoryTO {

    private Long id;
    private String name;

    public static SellerCategoryTO mapper(Category category)
    {
        SellerCategoryTO response = new SellerCategoryTO();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }

}
