package com.bootcampproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Seller")
@EntityListeners(AuditingEntityListener.class)
public class Seller extends AuditingInfo {
    @Id
    private Long id;
    @Column(unique = true)
    @Size(min = 15, max = 15, message = "Invalid GST number")
    private String gst;
    /*@Digits(integer = 10, fraction = 0, message = "Enter only digits without +91 ")*/
    @Size(min = 10,max = 10,message = "Enter 10 digits without +91")
    private String companyContact;
    @Column(unique = true)
    private String companyName;
    @OneToOne
    @MapsId
    private User user;
}