package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Data
public class OrderProduct extends AuditingInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Orders order;
    @Positive(message = "Cannot enter quantity in negative")
    @NotBlank(message = "Quantity cannot be blank")
    @NotNull(message = "Quantity cannot be Null")
    private Long quantity;

    @Digits(integer = 10,fraction = 5,message = "Enter price in correct format")
    @Positive(message = "Price are to be only positive")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;
}
