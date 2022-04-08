package com.bootcampproject.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "Customer")
public class Customer extends AuditingInfo {

    @Id
    private Long id;
    @Size(min = 10,max = 10,message = "Enter 10 digits without +91")
    private String contact;
    @OneToOne
    @MapsId
    private User user;

    private Date activationTokenAt;
    private String activationToken;

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)*/
    @Transient
    private List<Address> addressList;
}