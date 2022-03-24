package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String state;
    private String country;
    private String address_line;
    private Integer zipcode;
    private String label;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}