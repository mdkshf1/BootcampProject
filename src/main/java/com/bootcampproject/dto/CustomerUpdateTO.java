package com.bootcampproject.dto;


import lombok.Data;

@Data
public class CustomerUpdateTO {
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String phoneNumber;
}
