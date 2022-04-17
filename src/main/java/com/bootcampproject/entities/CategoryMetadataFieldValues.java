package com.bootcampproject.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@IdClass(CategoryMetadataFieldValuesPK.class)
public class CategoryMetadataFieldValues extends AuditingInfo{

    @Id
    @ManyToOne
    private CategoryMetadataField categoryMetadataField;

    @Id
    @ManyToOne
    private Category category;

    private String categoryValues;

}
