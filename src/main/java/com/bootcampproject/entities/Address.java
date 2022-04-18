package com.bootcampproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "City Cannot be Blank")
    @NotNull(message = "City cannot be Null")
    private String city;
    @Size(min = 2, message = "minimum you can enter is your state code having two characters")
    @NotNull(message = "State cannot be Null")
    @NotBlank(message = "State cannot be Blank")
    private String state;
    @Size(min = 2,message = "Minimum you can enter is your country code having 2 characters")
    @NotNull(message = "Country Cannot be Null")
    @NotBlank(message = "Country cannot be Blank")
    private String country;
    @Size(min = 2, message = "minimum characters to enter is 2")
    @NotNull(message = "AddressLine cannot be Null")
    @NotBlank(message = "AddressLine cannot be Blank")
    private String addressLine;
    @NotBlank(message = "Zipcode cannot be Blank")
    @NotNull(message = "Zipcode cannot be Null")
    @Size(min = 6, max = 6, message = "Enter a valid ZipCode")
    private String zipCode;
    @NotBlank(message = "Label cannot be Blank")
    @NotNull(message = "Label cannot be Null")
    @Size(min = 5, message = "Label should be at least 5 characters")
    private String label;
    private boolean isDeleted = false;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}