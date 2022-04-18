package com.bootcampproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterDetailsForCustomerTO {
    private List<SellerCategoryResponseTO> sellerCategoryResponseTO;
    private List<String> brands;
}
