package com.bootcampproject.dto;

import com.bootcampproject.entities.Product;
import com.bootcampproject.entities.ProductReview;
import com.bootcampproject.entities.ProductVariation;
import lombok.Data;

import java.util.List;

@Data
public class CustomerProductResponseTO {

    private Long id;
    private String sellerMail;
    private String name;
    private String description;
    private SellerCategoryTO category;
    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    private String brand;

    public static CustomerProductResponseTO mapper(Product product)
    {
        CustomerProductResponseTO response = new CustomerProductResponseTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        response.setSellerMail(product.getSeller().getUser().getEmail());
        response.setDescription(product.getDescription());
        response.setCategory(SellerCategoryTO.mapper(product.getCategory()));
        response.setIsCancellable(product.getIsCancellable());
        response.setIsReturnable(product.getIsReturnable());
        return response;
    }
}
