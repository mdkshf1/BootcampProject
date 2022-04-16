package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Customer customer;

    private Double amountPaid;
    private Date dateCreated;
    private String paymentMethod;
    private String CustomerAddressCity;
    private String CustomerAddressState;
    private String CustomerAddressCountry;
    private String CustomerAddressZipCode;
    private String CustomerAddressLabel;
    private String CustomerAddressLine;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

/*    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orders")
    private OrderProduct orderProduct;*/
}
