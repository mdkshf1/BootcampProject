package com.bootcampproject.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;


@Data
@Entity
@Table(name = "Seller")
public class Seller extends AuditingInfo{
    @Id
    private Long id;
    @Column(unique = true)
    @Size(min =15 , max = 15 , message = "Invalid GST number")
    private String gst;
    @Digits(integer = 10,fraction = 0,message = "Enter only digits without +91 ")
    private Long companyContact;
    @Column(unique = true)
    private String companyName;
    @OneToOne
    @MapsId
    private User user;
}