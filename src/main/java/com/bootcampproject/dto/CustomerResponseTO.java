package com.bootcampproject.dto;

import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.User;
import lombok.Data;

@Data
public class CustomerResponseTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean isActive;
    private String contact;

    public static CustomerResponseTO mapper(User user)
    {
        CustomerResponseTO customer = new CustomerResponseTO();
        customer.setId(user.getId());
        customer.setFirstName(user.getFirstName());
        customer.setMiddleName(user.getMiddleName());
        customer.setLastName(user.getLastName());
        customer.setActive(user.isActive());
        Customer customer1 = user.getCustomer();
        customer.setContact(customer1.getContact());
        return customer;
    }
}
