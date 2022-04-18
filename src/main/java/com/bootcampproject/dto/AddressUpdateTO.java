package com.bootcampproject.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AddressUpdateTO {
    @Size(min = 2, message = "minimum you can enter is your city code having two characters")
    private String city;
    @Size(min = 2, message = "minimum you can enter is your state code having two characters")
    private String state;
    @Size(min = 2,message = "Minimum you can enter is your country code having 2 characters")
    private String country;
    @Size(min = 2, message = "minimum characters to enter is 2")
    private String AddressLine;
    @Size(min = 6, max = 6, message = "Enter a valid ZipCode")
    private String zipCode;
    @Size(min = 5, message = "Label should be at least 5 characters")
    private String label;
}