package com.bootcampproject.entities;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "User")
@NoArgsConstructor
public class User extends AuditingInfo {
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
    @Size(min = 8, max = 15, message = "Password should have 8 to 15 characters with atleast 1 upper-case letter, 1 lower case letter, 1 special character and 1 number")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private String password;
    private boolean isDeleted = false;
    private boolean isActive = false;
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
    @ManyToMany
    private List<Role> role;
}
