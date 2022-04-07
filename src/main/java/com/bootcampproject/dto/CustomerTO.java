package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTO extends UserTO{
    @Id
    private Long id;
    @Size(min = 10, max = 10, message = "Enter 10 digits without +91")
    private String contact;
    private Date activationTokenAt;
    private String activationToken;

    private List<Address> addressList;

    public static Customer createCustomer(CustomerTO customerTO)
    {
        System.out.println("Inside customer to");
        Customer customer = new Customer();
        customer.setContact(customerTO.getContact());
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setActivationTokenAt(new Date());
        return customer;
    }
}