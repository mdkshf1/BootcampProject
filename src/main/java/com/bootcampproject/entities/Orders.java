package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Orders extends AuditingInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Customer customer;
    @Digits(integer = 10,fraction = 2,message = "Amount should be in correct format")
    @Positive(message = "Amount can only be positive")
    private Double amountPaid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @NotNull(message = "Payment method cannot be null")
    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;
    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City cannot be blank")
    private String customerAddressCity;
    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State cannot be blank")
    private String customerAddressState;
    @NotNull(message = "Country cannot be null")
    @NotBlank(message = "Country cannot be blank")
    private String customerAddressCountry;
    @NotNull(message = "ZipCode cannot be null")
    @NotBlank(message = "ZipCode cannot be blank")
    @Size(min = 6,max = 6,message = "Enter zipcode in correct format")
    private String customerAddressZipCode;
    @NotNull(message = "Label cannot be null")
    @NotBlank(message = "Label cannot be blank")
    private String customerAddressLabel;
    @NotNull(message = "AddressLine cannot be null")
    @NotBlank(message = "AddressLine cannot be blank")
    private String customerAddressLine;
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
}
