package com.bootcampproject.entities;


import javax.persistence.*;

@Entity
@IdClass(ProductReviewPK.class)
public class ProductReview {
    @Id
    @ManyToOne
    private Customer customer;

    @Id
    @ManyToOne
    private Product products;
    private String reviews;
    private Integer rating;
}
