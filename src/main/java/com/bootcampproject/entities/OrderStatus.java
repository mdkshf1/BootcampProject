package com.bootcampproject.entities;

import com.bootcampproject.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderStatus extends AuditingInfo{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /*private String fromStatus;*/
    @Enumerated(EnumType.STRING)
    private Status fromStatus;

    @Enumerated(EnumType.STRING)
    private Status toStatus;

    private String transitionNotesComments;

    @MapsId
    @OneToOne
    private OrderProduct orderProduct;

}
