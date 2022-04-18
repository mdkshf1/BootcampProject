package com.bootcampproject.dto;

import com.bootcampproject.entities.OrderProduct;
import com.bootcampproject.entities.ProductVariation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@Data
public class ProductVariationTO {
    private Long id;
    private Long productId;
    private Integer quantityAvailable;
    @Digits(integer = 20,fraction = 3,message = "Price should be in correct format")
    @Positive(message = "Price should be positive")
    private Double price;
    private String metadata;
    private String primaryImageName;
    private Boolean isActive=true;

    public static ProductVariationTO mapper(ProductVariation productVariation)
    {
        ProductVariationTO product = new ProductVariationTO();
        product.setProductId(productVariation.getProduct().getId());
        product.setId(productVariation.getId());
        product.setIsActive(productVariation.getIsActive());
        product.setQuantityAvailable(productVariation.getQuantityAvailable());
        product.setPrimaryImageName(productVariation.getPrimaryImageName());
        product.setPrice(productVariation.getPrice());
        product.setMetadata(productVariation.getMetadata());
        return product;
    }
}
