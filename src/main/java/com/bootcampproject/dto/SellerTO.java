package com.bootcampproject.dto;

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
import javax.validation.constraints.Size;


@Data
public class SellerTO extends UserTO {

    @Email
    private String email;
    @Id
    private Long id;
    @Column(unique = true)
    @Size(min = 15, max = 15, message = "Invalid GST number")
    private String gst;
    @Digits(integer = 10, fraction = 0, message = "Enter only digits without +91 ")
    private Long companyContact;
    @Column(unique = true)
    private String companyName;
    @JsonIgnore
    @OneToOne
    @MapsId
    private User user;

    public static Seller mapper(SellerTO sellerTO,User user)
    {
        Seller seller = new Seller();
        seller.setCompanyName(sellerTO.getCompanyName());
        seller.setCompanyContact(sellerTO.getCompanyContact());
        seller.setGst(sellerTO.getGst());
        seller.setUser(user);
        return seller;
    }

}
