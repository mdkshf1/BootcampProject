package com.bootcampproject.entities;

import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
public class ProductVariation extends AuditingInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Product product;
    @Positive(message = "Quantity should be positive")
    @NotNull(message = "Quantity cannot be null")
    private Integer quantityAvailable;
    @Digits(integer = 20,fraction = 3,message = "Price should be in correct format")
    @Positive(message = "Price should be positive")
    private Double price;
    private String metadata;
    private String primaryImageName;
    private Boolean isActive;
    @OneToMany
    private List<OrderProduct> orderProduct;
}
