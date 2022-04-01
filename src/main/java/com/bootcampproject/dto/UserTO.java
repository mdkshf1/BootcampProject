package com.bootcampproject.dto;

import com.bootcampproject.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
public class UserTO {

    @Column(unique = true)
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    /*    @Size(min = 8, max = 15, message = "Password should have 8 to 15 characters with atleast 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")*/
    private String password;
    private String confirmPassword;
    @JsonIgnore
    private List<Role> roles;
}
