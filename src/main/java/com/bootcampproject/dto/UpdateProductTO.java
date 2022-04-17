package com.bootcampproject.dto;

import lombok.Data;
@Data
public class UpdateProductTO {

    private Long id;
    private String name;
    private String description;
    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    private String brand;
    private Boolean isActive = false;
    private Boolean isDeleted = false;
}
