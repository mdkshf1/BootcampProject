package com.bootcampproject.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductEnterTO {

    @NotNull
    private String name;
    private String description;
    @NotNull
    private Long categoryId;
    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    @NotNull
    private String brand;
}
