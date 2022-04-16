package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    private Long id;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> category;
    @ManyToOne(cascade ={CascadeType.ALL})
    @JoinColumn(name = "parentCategoryId")
    private Category parentCategory;
    private String name;

    @OneToOne(mappedBy = "category")
    private Product product;

/*    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> product;*/

    

/*    @OneToMany
    private List<CategoryMetadataField> categoryMetadataField;*/

    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;
}
