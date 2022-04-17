package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import lombok.Data;

@Data
public class AdminSellerResponseTO {

    private Long id;
    private String firstname;
    private String middleName;
    private String lastName;
    private String email;
    private Boolean isActive;
    private String companyName;
    private String companyContact;
    private Address address;

    public static AdminSellerResponseTO mapper(User user)
    {
        AdminSellerResponseTO response = new AdminSellerResponseTO();
        Address address1 = user.getAddress().remove(0);
        Address sellerAddress = new Address();
        Seller seller = user.getSeller();
        response.setId(user.getId());
        response.setFirstname(user.getFirstName());
        response.setMiddleName(user.getMiddleName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setIsActive(user.isActive());
        response.setCompanyName(seller.getCompanyName());
        response.setCompanyContact(seller.getCompanyContact());
        sellerAddress.setCity(address1.getCity());
        sellerAddress.setState(address1.getState());
        sellerAddress.setCountry(address1.getCountry());
        sellerAddress.setAddressLine(address1.getAddressLine());
        sellerAddress.setLabel(address1.getLabel());
        sellerAddress.setZipCode(address1.getZipCode());
        response.setAddress(sellerAddress);
        return response;
    }

}
