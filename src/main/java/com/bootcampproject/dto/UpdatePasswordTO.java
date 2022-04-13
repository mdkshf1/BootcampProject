package com.bootcampproject.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordTO {

    @Size(min = 8, max = 15, message = "Password should have 8 to 15 characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message = "Password should have atleast 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
    private String password;
    private String confirmPassword;
}
