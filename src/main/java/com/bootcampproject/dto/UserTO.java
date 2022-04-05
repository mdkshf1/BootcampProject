package com.bootcampproject.dto;

import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.UserRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserTO {

    @Autowired
    private static PasswordEncoder passwordEncoder;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",message = "Enter mail in correct format or invalid mail")
    private String email;
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    @Size(min = 8, max = 15, message = "Password should have 8 to 15 characters with atleast 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
      /*  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")*/
    private String password;
    @NotNull
    private String confirmPassword;

    @JsonIgnore
    private Set<Role> roles;


    public static User mapper(UserTO userTO) {
        UserTO userto = new UserTO();
        userto.setEmail(userTO.getEmail());
        userto.setPassword(userTO.getPassword());
        userto.setFirstName(userTO.getFirstName());
        userto.setMiddleName(userTO.getMiddleName());
        userto.setLastName(userTO.getLastName());
        userto.setRoles(userTO.getRoles());
        return User.create(userto);
    }
}
