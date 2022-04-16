package com.bootcampproject.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class CategoryMetadataField {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;
/*    @OneToMany(mappedBy = "categoryMetadataField")
    private List<Category> category;*/

    @OneToMany(mappedBy = "categoryMetadataField")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;
}
