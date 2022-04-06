package com.bootcampproject.entities;

import com.bootcampproject.dto.SellerTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Seller")
public class Seller extends AuditingInfo {
    @Id
    private Long id;
    @Column(unique = true)
    @Size(min = 15, max = 15, message = "Invalid GST number")
    private String gst;
    @Digits(integer = 10, fraction = 0, message = "Enter only digits without +91 ")
    private Long companyContact;
    @Column(unique = true)
    private String companyName;
    @OneToOne
    @MapsId
    private User user;

    public static Seller setDetails(SellerTO sellerTO)
    {
        Seller seller = new Seller();
        seller.setCompanyName(sellerTO.getCompanyName());
        seller.setCompanyContact(sellerTO.getCompanyContact());
        seller.setGst(sellerTO.getGst());
        return seller;
    }
}