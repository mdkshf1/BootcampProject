package com.bootcampproject.entities;


import com.bootcampproject.dto.CustomerTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "Customer")
public class Customer extends AuditingInfo {

    @Id
    private Long id;
    @Digits(integer = 10, fraction = 0, message = "only numbers are allowed ")
    @Pattern(regexp = "((\\+91)|0)[.\\- ]?[0-9][.\\- ]?[0-9][.\\- ]?[0-9]")
    private String contact;
    @OneToOne
    @MapsId
    private User user;

    private Date activationTokenAt;
    private String activationToken;

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)*/
    @Transient
    private List<Address> addressList;

    public static Customer createCustomer(CustomerTO customerTO)
    {
        Customer customer = new Customer();
        customer.setContact(customerTO.getContact());
        customer.setActivationToken(UUID.randomUUID().toString());
        customer.setActivationTokenAt(new Date());
/*        Address address = new Address();
      List<Address> addres = new ArrayList<>();
        for (Address addresses: addressList
             ) {
            address.setCity(addresses.getCity());
            address.setState(addresses.getState());
            address.setCountry(addresses.getCountry());
            address.setAddress_line(addresses.getAddress_line());
            address.setLabel(addresses.getLabel());
            address.setZipcode(addresses.getZipcode());
            addres.add(address);
        }
        customer.setAddressList(addres);*/
        return customer;
    }

}