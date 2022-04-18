package com.bootcampproject.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Customer")
public class Customer extends AuditingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 10,max = 10,message = "Enter 10 digits without +91")
    @Positive(message = "Phone Number cannot be negative")
    private String contact;
    @OneToOne
    @MapsId
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date activationTokenAt;

    private String activationToken;

    @Transient
    private List<Address> addressList;

    @OneToMany(mappedBy = "customer")
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders;
}