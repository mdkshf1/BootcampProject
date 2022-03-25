package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 2, message = "minimum you can enter is your city code having two characters")
    private String city;
    @Size(min = 2, message = "minimum you can enter is your state code having two characters")
    private String state;
    @Size(min = 2, message = "minimum you can enter your country code having two characters")
    private String country;
    @Size(min = 2, message = "minimum characters to enter is 2")
    private String address_line;
    @Size(min = 6, max = 6, message = "Zip code should be of 6 digits")
    @Positive
    private Integer zipcode;
    @Size(min =5, message = "Label should be atleast 5 characters")
    private String label;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}