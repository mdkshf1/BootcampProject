package com.bootcampproject.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderProduct {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Orders order;
    private Long quantity;
    private double price;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ProductVariation productVariation;


/*    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;*/

}
