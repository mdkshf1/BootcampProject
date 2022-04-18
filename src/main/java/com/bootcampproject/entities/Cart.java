package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
@Entity
@IdClass(CartPK.class)
@Data
public class Cart extends AuditingInfo{

    @Id
    @OneToOne
    private Customer customer;

    @Id
    @ManyToOne
    private ProductVariation productVariation;
    @NotNull(message = "Quantity cannot be Null")
    private Long quantity;

    private boolean isWishlistItem;

}
