package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category extends AuditingInfo {
    @Id
    private Long id;

    @ManyToOne(cascade ={CascadeType.ALL})
    @JoinColumn(name = "parentCategoryId")
    private Category parentCategory;

    private String name;

    @OneToOne(mappedBy = "category")
    private Product product;

    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;
}
