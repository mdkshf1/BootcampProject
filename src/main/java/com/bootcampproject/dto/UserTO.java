package com.bootcampproject.dto;

import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserTO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

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
    private Set<Role> roles;


    public UserTO create(UserTO userTO)
    {
        User user = new User();
        user.setEmail(userTO.getEmail());
        user.setFirstName(userTO.getFirstName());
        user.setMiddleName(userTO.getMiddleName());
        user.setLastName(userTO.getLastName());
        user.setPassword(passwordEncoder.encode(userTO.getPassword()));
        user.setActive(false);
        user.setDeleted(false);
        user.setExpired(false);
        user.setLocked(true);
        user.setInvalidAttemptCount(0);
        user.setRoles(userTO.getRoles());
        userRepo.save(user);
        System.out.println(user);
        return userTO;
    }

}
