package com.bootcampproject.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String fromStatus;
    private String toStatus;
    private String transitionNotesComments;
    @MapsId
    @OneToOne
    private OrderProduct orderProduct;
}
