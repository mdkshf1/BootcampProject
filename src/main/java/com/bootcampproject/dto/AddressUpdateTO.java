package com.bootcampproject.dto;

import lombok.Data;

@Data
public class AddressUpdateTO {
    private Long id;
    private String city;
    private String state;
    private String country;
    private String AddressLine;
    private String zipCode;
    private String label;
}
