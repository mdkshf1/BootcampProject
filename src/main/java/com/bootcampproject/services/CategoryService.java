package com.bootcampproject.services;


import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.CategoryMetadataField;
import com.bootcampproject.exceptions.DataNotFoundException;
import com.bootcampproject.exceptions.EmptyListException;
import com.bootcampproject.exceptions.DataAlreadyPresentException;
import com.bootcampproject.repositories.CategoryMetadataFieldRepo;
import com.bootcampproject.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMetadataFieldRepo metadataRepo;

    public Long addMetadata(String name)
    {
        if (metadataRepo.findByName(name) != null)
            throw new DataAlreadyPresentException("Metadata with this name already existed");
        CategoryMetadataField metadataField = new CategoryMetadataField();
        metadataField.setName(name);
        metadataRepo.save(metadataField);
        CategoryMetadataField metadata = metadataRepo.findByName(name);
        Long id = metadata.getId();
        return id;
    }

    public List<CategoryMetadataField> getAllMetadata(Pageable pageable)
    {
        Page<CategoryMetadataField> categoryMetadataFields = metadataRepo.findAll(pageable);
        if (categoryMetadataFields.isEmpty())
            throw new EmptyListException("Metadata is empty");
        return categoryMetadataFields.getContent();
    }
    public Long addCategory(String name,Long parentId)
    {
        if (categoryRepo.findByName(name) != null)
            throw new DataAlreadyPresentException("Category with this name is already present");
        Category category = new Category();
        category.setName(name);
        Category parentCategory = new Category();
        parentCategory = category.getParentCategory();
        parentCategory.setId(parentId);
        category.setParentCategory(parentCategory);
        categoryRepo.save(category);
        category = categoryRepo.findByName(name);
        Long id = category.getId();
        return id;
    }
    //parent tk ka kaise krenge oata krna hai
    public Category getCategory(Long id)
    {
        return categoryRepo.getById(id);
    }
    public List<Category> getAllCategory(Pageable pageable)
    {
        Page<Category> categories = categoryRepo.findAll(pageable);
        if (categories.isEmpty())
            throw new EmptyListException("No categories Present");
        return categories.getContent();
    }
    public Category updateCategory(Long id,String name) {
        Category category = categoryRepo.findById(id).get();
        if (category == null)
            throw new DataNotFoundException("Category with this id not found");
        category.setName(name);
        return categoryRepo.save(category);
    }
}