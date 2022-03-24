package com.bootcampproject.entities;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Customer extends AuditingInfo{
    @Id
    private int userId;
    private Long contact;
    @OneToOne
    @MapsId
    private User user;
}