package com.bootcampproject.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductVariation {

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Product product;
    private Long quantityAvailable;
    private Double price;
    private String metadata;
    private String primaryImageName;
    private boolean isActive;

    @OneToOne(mappedBy = "productVariation")
    private OrderProduct orderProduct;

    @OneToOne(mappedBy = "productVariation")
    private Cart cart;

/*
    @OneToOne(mappedBy = "productVariation")
    private Cart cart;*/

}
