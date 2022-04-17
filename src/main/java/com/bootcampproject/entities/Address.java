package com.bootcampproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Address")
@Where(clause = "is_deleted=false")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "minimum you can enter is your city code having two characters")
    @NotBlank
    @NotNull
    private String city;
    @Size(min = 2, message = "minimum you can enter is your state code having two characters")
    @NotNull
    @NotBlank
    private String state;
    @NotNull
    @NotBlank
    private String country;
    @Size(min = 2, message = "minimum characters to enter is 2")
    @NotNull
    @NotBlank
    private String addressLine;
  /*  @Size(min = 6, max = 6, message = "Zip code should be of 6 digits")*/
    @NotBlank
    @NotNull
    @Size(min = 6,max = 6,message = "Enter a valid ZipCode")
    private String zipCode;
    @NotBlank
    @NotNull
    @Size(min = 5, message = "Label should be atleast 5 characters")
    private String label;

    private boolean isDeleted=false;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}