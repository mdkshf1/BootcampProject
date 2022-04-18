package com.bootcampproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryMetadataField extends AuditingInfo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Metadata Field Name cannot be null")
    @NotBlank(message = "Metadata Field name cannot be Blank")
    private String name;

    @OneToMany(mappedBy = "categoryMetadataField")
    @ToString.Exclude
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;
}
