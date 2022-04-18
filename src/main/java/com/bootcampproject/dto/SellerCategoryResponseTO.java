package com.bootcampproject.dto;

import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.CategoryMetadataField;
import lombok.Data;
import java.util.Set;

@Data
public class SellerCategoryResponseTO {

    private SellerCategoryTO category;
    private MetadataResponseTO metadataField;
    private Set<String> values;
    private String parentNodeName;

    public static SellerCategoryResponseTO mapper(Category category1, CategoryMetadataField metadataField1, Set<String> values, String parentNodeName)
    {
        SellerCategoryResponseTO categoryResponse = new SellerCategoryResponseTO();
        Category category = new Category();
        CategoryMetadataField metadataField = new CategoryMetadataField();
        category.setId(category1.getId());
        category.setName(category1.getName());
        metadataField.setId(metadataField1.getId());
        metadataField.setName(metadataField1.getName());
        categoryResponse.setCategory(SellerCategoryTO.mapper(category));
        categoryResponse.setMetadataField(MetadataResponseTO.mapper(metadataField));
        categoryResponse.setValues(values);
        categoryResponse.setParentNodeName(parentNodeName);
        return categoryResponse;
    }
}
