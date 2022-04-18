package com.bootcampproject.dto;

import com.bootcampproject.entities.CategoryMetadataField;
import lombok.Data;
@Data
public class MetadataResponseTO {

    private Long id;
    private String name;

    public static MetadataResponseTO mapper(CategoryMetadataField metadataField)
    {
        MetadataResponseTO response = new MetadataResponseTO();
        response.setId(metadataField.getId());
        response.setName(metadataField.getName());
        return response;
    }

}
