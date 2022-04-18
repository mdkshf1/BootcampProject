package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.UserRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserTO {

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",message = "Enter mail in correct format or invalid mail")
    private String email;
    @NotBlank(message = "First name must not be null")
    private String firstName;
    private String middleName;
    @NotNull(message = "Last Name could not be null")
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
    @NotNull
    @Size(min = 8, max = 15,message = "Password should be of 8 to 15 characters long")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message = "Password should have at least 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
    private String password;
    @NotNull
    private String confirmPassword;
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Seller seller;
    @JsonIgnore
    private Set<Role> roles;
    private List<Address> addressList;

}
