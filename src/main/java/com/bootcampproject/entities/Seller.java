package com.bootcampproject.entities;

import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Seller")
public class Seller extends AuditingInfo {
    @Id
    private Long id;
    @Column(unique = true)
    @Size(min = 15, max = 15, message = "Invalid GST number")
    private String gst;
    @Size(min = 10,max = 10,message = "Enter 10 digits without +91")
    private String companyContact;
    @Column(unique = true)
    private String companyName;
    @OneToOne
    @MapsId
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "seller")
    private List<Product> products;
}