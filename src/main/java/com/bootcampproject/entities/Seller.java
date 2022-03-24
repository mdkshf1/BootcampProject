package com.bootcampproject.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Data
@Entity
public class Seller extends AuditingInfo{
    @Id
    private Long id;
    private String gst;
    private Long companyContact;
    private String companyName;
    @OneToOne
    @MapsId
    private User user;
}