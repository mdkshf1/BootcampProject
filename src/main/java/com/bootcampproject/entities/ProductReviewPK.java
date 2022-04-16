package com.bootcampproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductReviewPK implements Serializable {
    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product products;
}