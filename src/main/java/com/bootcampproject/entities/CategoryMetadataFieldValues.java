package com.bootcampproject.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@IdClass(CategoryMetadataFieldValuesPK.class)
public class CategoryMetadataFieldValues extends AuditingInfo{

    @Id
    @ManyToOne
    private CategoryMetadataField categoryMetadataField;

    @Id
    @ManyToOne
    private Category category;

    @ElementCollection
    private Set<String> categoryValues;

}
