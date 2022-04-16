package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@IdClass(CartPK.class)
@Data
public class Cart {

    @Id
    @OneToOne
    private Customer customer;

    @Id
    @OneToOne
    private ProductVariation productVariation;

    private Long quantity;

    private boolean isWishlistItem;

}
