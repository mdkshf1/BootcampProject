package com.bootcampproject.dto;

import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.Product;
import lombok.Data;

@Data
public class ProductTO {
    private Long id;
    private String name;
    private String description;
    private SellerCategoryTO category;
    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    private String brand;
    private Boolean isActive = false;
    private Boolean isDeleted = false;

    public static ProductTO mapper(Product product)
    {
        ProductTO response = new ProductTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setBrand(product.getBrand());
        response.setIsActive(product.getIsActive());
        response.setIsCancellable(product.getIsCancellable());
        response.setIsReturnable(product.getIsReturnable());
        response.setCategory(SellerCategoryTO.mapper(product.getCategory()));
        response.setIsDeleted(product.getIsDeleted());
        return response;
    }
}
