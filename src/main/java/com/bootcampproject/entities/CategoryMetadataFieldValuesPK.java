
package com.bootcampproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryMetadataFieldValuesPK implements Serializable {

    private Long categoryMetadataField;

    private Long category;
}
