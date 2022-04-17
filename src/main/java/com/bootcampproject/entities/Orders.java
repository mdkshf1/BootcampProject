package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Orders extends AuditingInfo{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Customer customer;

    private Double amountPaid;
    private Date dateCreated;
    private String paymentMethod;
    private String customerAddressCity;
    private String customerAddressState;
    private String customerAddressCountry;
    private String customerAddressZipCode;
    private String customerAddressLabel;
    private String customerAddressLine;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

/*    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "orders")
    private OrderProduct orderProduct;*/
}
