package com.bootcampproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends AuditingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade ={CascadeType.ALL})
    @JoinColumn(name = "parentCategoryId")
    private Category parentCategory;

    @NotNull(message = "Category Name cannot be Null")
    @NotBlank(message = "Category Cannot be Blank")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> product;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;
}
