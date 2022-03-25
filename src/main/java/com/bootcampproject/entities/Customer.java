package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;


@Data
@Entity
@Table(name = "Customer")
public class Customer extends AuditingInfo{

    @Id
    private Long id;
    @Digits(integer = 10,fraction = 0,message = "only numbers are allowed ")
    @Pattern(regexp = "((\\+91)|0)[.\\- ]?[0-9][.\\- ]?[0-9][.\\- ]?[0-9]")
    private Long contact;
    @OneToOne
    @MapsId
    private User user;
}