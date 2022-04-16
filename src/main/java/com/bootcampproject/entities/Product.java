package com.bootcampproject.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {
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

    private boolean isCancellable;
    private boolean isReturnable;
    private String brand;
    private boolean isActive;
    private boolean isDeleted;
    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariation;

    @OneToMany(mappedBy = "products")
    private List<ProductReview> productReviews;
}
