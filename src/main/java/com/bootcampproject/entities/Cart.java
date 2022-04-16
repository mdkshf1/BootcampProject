package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
@Entity
@IdClass(CartPK.class)
@Data
public class Cart {

    @Id
    @OneToOne
    private Customer customer;

    @Id
    @ManyToOne
    private ProductVariation productVariation;


    private Long quantity;

    private boolean isWishlistItem;

}
