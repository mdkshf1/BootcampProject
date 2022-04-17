package com.bootcampproject.entities;

import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ProductVariation extends AuditingInfo{

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Product product;
    private Long quantityAvailable;
    private Double price;
    private String metadata;
    private String primaryImageName;
    private Boolean isActive;

    @OneToMany
    private List<OrderProduct> orderProduct;
}
