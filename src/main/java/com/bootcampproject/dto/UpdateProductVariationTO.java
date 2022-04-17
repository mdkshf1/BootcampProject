package com.bootcampproject.dto;

import lombok.Data;

@Data
public class UpdateProductVariationTO {
    private Long id;
    private Long quantityAvailable;
    private Double price;
    private String metadata;
    private String primaryImageName;
    private Boolean isActive;
}
