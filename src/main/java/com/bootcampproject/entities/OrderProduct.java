package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class OrderProduct extends AuditingInfo{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Orders order;


    private Long quantity;

    private double price;

    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)*/
    @ManyToOne
    private ProductVariation productVariation;


    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;

/*    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;*/

}
