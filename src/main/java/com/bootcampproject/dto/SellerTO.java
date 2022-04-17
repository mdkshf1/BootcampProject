package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Locale;


@Data
public class SellerTO extends UserTO {

    private Long id;
    @Size(min = 15, max = 15, message = "Invalid GST number")
    @NotNull(message = "GST should be valid as per govt. norms!")
    @NotBlank(message = "GST can not be blank!")
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$", message = "Please provide a valid GST number")
    private String gst;
    @Size(min = 10,max = 10 , message = "Enter only 10 digits without +91" )
    @NotBlank(message = "Company Contact could not be blank")
    @NotNull(message = "Company contact could not be null")
    private String companyContact;
    @NotNull(message = "Company Name could not be null")
    @NotBlank(message = "Company name could not be blank")
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
