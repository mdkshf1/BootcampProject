package com.bootcampproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CartPK implements Serializable {

    private Long customer;

    private Long productVariation;

}
