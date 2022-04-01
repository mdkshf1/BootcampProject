package com.bootcampproject.entities;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.repositories.UserRepo;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "User")
@NoArgsConstructor
@Component
public class User extends AuditingInfo{


    @Transient
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transient
    @Autowired
    private UserRepo userRepo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NonNull
    private String firstName;
    private String middleName;
    private String lastName;
/*    @Size(min = 8, max = 15, message = "Password should have 8 to 15 characters with atleast 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")*/
    private String password;
    private boolean isDeleted = false;
    private boolean isActive = true;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private Integer invalidAttemptCount = 0;
    @LastModifiedDate
    private Date passwordUpdateDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address;
    @OneToOne(mappedBy = "user")
    private Customer customer;
    @OneToOne(mappedBy = "user")
    private Seller seller;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles;

/*
    public User(User user) {
        this.password = user.getPassword();
        this.email = user.getEmail();
    }*/

    @Transient
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
        userRepo.save(user);
        System.out.println(user);
        return userTO;
    }

}
