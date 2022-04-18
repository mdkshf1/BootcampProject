package com.bootcampproject.entities;

import com.bootcampproject.dto.UserTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "User",uniqueConstraints = @UniqueConstraint(columnNames = {"id","email"}))
@NoArgsConstructor
@Slf4j
public class User extends AuditingInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull(message = "firstname cannot be null")
    @NotBlank(message = "Firstname cannot be Blank")
    @Column(nullable = false)
    private String firstName;
    private String middleName;
    @NotNull(message = "Lastname cannot be null")
    @NotBlank(message = "Lastname cannot be Blank")
    @Column(nullable = false)
    private String lastName;
    private String password;
    private boolean isDeleted = false;
    private boolean isActive = true;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private Integer invalidAttemptCount = 0;
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordUpdateDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address;
    @OneToOne(mappedBy = "user")
    private Customer customer;
    @OneToOne(mappedBy = "user")
    private Seller seller;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    private Set<Role> roles;
    @Transient
    private List<GrantedAuthority> grantedAuthorities;
    private String forgotPasswordToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date forgotPasswordAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User create(UserTO userTO)
    {
        User user = new User();
        user.setEmail(userTO.getEmail());
        user.setFirstName(userTO.getFirstName());
        user.setMiddleName(userTO.getMiddleName());
        user.setLastName(userTO.getLastName());
        user.setActive(false);
        user.setDeleted(false);
        user.setLocked(false);
        user.setExpired(false);
        user.setInvalidAttemptCount(0);
        user.setRoles(userTO.getRoles());
        log.info("role set for admin");
        user.setForgotPasswordToken(UUID.randomUUID().toString());
        System.out.println("inside user "+user);
        return user;
    }
}
