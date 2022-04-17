package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import lombok.Data;

@Data
public class SellerUpdateTO {

    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String gst;
    private String companyContact;
    private String companyName;
    private Address address;
}
