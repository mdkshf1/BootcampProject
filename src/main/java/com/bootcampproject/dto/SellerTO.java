package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class SellerTO extends UserTO {

    @Id
    private Long id;
    @Column(unique = true)
    @Size(min = 15, max = 15, message = "Invalid GST number")
    private String gst;
    @Size(min = 10,max = 10 , message = "Enter only digits without +91" )
    private String companyContact;
    @Column(unique = true)
    @NotNull
    private String companyName;

    private Address address;

    public static Seller mapper(SellerTO sellerTO,User user)
    {
        Seller seller = new Seller();
        seller.setCompanyName(sellerTO.getCompanyName());
        seller.setCompanyContact(sellerTO.getCompanyContact());
        seller.setGst(sellerTO.getGst());
        Address address= sellerTO.getAddress();
        Address sellerAddress = new Address();
        sellerAddress.setCity(address.getCity());
        sellerAddress.setState(address.getState());
        sellerAddress.setCountry(address.getCountry());
        sellerAddress.setAddressLine(address.getAddressLine());
        sellerAddress.setLabel(address.getLabel());
        sellerAddress.setZipCode(address.getZipCode());
        sellerAddress.setUser(user);
        seller.setUser(user);
        return seller;
    }

}
