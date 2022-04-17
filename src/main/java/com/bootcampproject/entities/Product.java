package com.bootcampproject.entities;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Where(clause = "is_deleted=false")
public class Product extends AuditingInfo{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Seller seller;

    private String name;
    private String description;
    @OneToOne
    private Category category;
/*    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;*/

    private Boolean isCancellable = false;
    private Boolean isReturnable = false;
    private String brand;
    private Boolean isActive = false;
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariation;

    @OneToMany(mappedBy = "products")
    private List<ProductReview> productReviews;
}
