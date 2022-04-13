package com.bootcampproject.dto;


import com.bootcampproject.entities.User;
import lombok.Data;

@Data
public class AdminCustomerResponseTO {

    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean isActive;

    public static AdminCustomerResponseTO getCustomer(User user)
    {
        AdminCustomerResponseTO customer = new AdminCustomerResponseTO();
        customer.setId(user.getId());
        customer.setEmail(user.getEmail());
        customer.setFirstName(user.getFirstName());
        customer.setMiddleName(user.getMiddleName());
        customer.setLastName(user.getLastName());
        customer.setActive(user.isActive());
        return customer;
    }
}
