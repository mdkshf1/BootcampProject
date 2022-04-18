package com.bootcampproject.entities;

import com.bootcampproject.enums.Status;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class OrderStatus extends AuditingInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status fromStatus;

    @Enumerated(EnumType.STRING)
    private Status toStatus;

    @Size(min = 5,message = "Enter comment with at least 5 characters")
    private String transitionNotesComments;
    @MapsId
    @OneToOne
    private OrderProduct orderProduct;

}
