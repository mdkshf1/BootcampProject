package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import lombok.Data;

import java.util.Optional;


@Data
public class SellerResponseTO {
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean isActive;
    private String companyName;
    private Address address;
    private String companyContact;

    public static SellerResponseTO getSeller(Seller sellers, User user)
    {
        SellerResponseTO seller = new SellerResponseTO();
        Address address = new Address();
        Address address1 = seller.getAddress();
        address.setCity(address1.getCity());
        address.setState(address1.getState());
        address.setCountry(address1.getCountry());
        address.setLabel(address1.getLabel());
        address.setAddressLine(address1.getAddressLine());
        address.setZipCode(address1.getZipCode());
        address.setUser(user);
        seller.setId(user.getId());
        seller.setEmail(user.getEmail());
        seller.setFirstName(user.getFirstName());
        seller.setMiddleName(user.getMiddleName());
        seller.setLastName(user.getLastName());
        seller.setActive(user.isActive());
        seller.setCompanyName(sellers.getCompanyName());
        seller.setCompanyContact(sellers.getCompanyContact());
        seller.setAddress(address);
        return seller;
    }
}
