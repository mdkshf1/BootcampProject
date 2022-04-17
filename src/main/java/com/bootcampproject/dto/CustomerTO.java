package com.bootcampproject.dto;

import com.bootcampproject.entities.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
public class CustomerTO extends UserTO{

    private Long id;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Enter phone number in correct format")
    private String phoneNumber;

    private Date activationTokenAt;

    private String activationToken;

    public static Customer createCustomer(CustomerTO customerTO)
    {
        Customer customer = new Customer();
        customer.setContact(customerTO.getPhoneNumber());
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setActivationTokenAt(new Date());
        return customer;
    }
}