package com.bootcampproject.services;


import com.bootcampproject.dto.CategoryResponseTO;
import com.bootcampproject.dto.FilterDetailsForCustomerTO;
import com.bootcampproject.dto.SellerCategoryResponseTO;
import com.bootcampproject.dto.SellerCategoryTO;
import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.CategoryMetadataField;
import com.bootcampproject.entities.CategoryMetadataFieldValues;
import com.bootcampproject.entities.Product;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.exceptions.DataAlreadyPresentException;
import com.bootcampproject.repositories.CategoryMetadataFieldRepo;
import com.bootcampproject.repositories.CategoryMetadataFieldValuesRepo;
import com.bootcampproject.repositories.CategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMetadataFieldRepo metadataRepo;

    @Autowired
    private CategoryMetadataFieldValuesRepo metadataFieldValuesRepo;

    @Transactional
    public Long addMetadata(String name) {
        CategoryMetadataField field = metadataRepo.findByName(name);
        if (field != null) {
            throw new DataAlreadyPresentException("Metadata with this name already existed");
        }
        CategoryMetadataField metadataField = new CategoryMetadataField();
        log.info("Here we are");
        metadataField.setName(name);
        log.info("setting");
        metadataRepo.save(metadataField);
        log.info("after saving");
        CategoryMetadataField metadata = metadataRepo.findByName(name);
        log.info("after finding");
        Long id = metadata.getId();
        log.info("after getting id");
        return id;
    }

    public List<CategoryMetadataField> getAllMetadata(Pageable pageable) {
        List<CategoryMetadataField> metadataField = new ArrayList<>();
        Page<CategoryMetadataField> metadataFieldPage = metadataRepo.findAll(pageable);
        if (metadataFieldPage.hasContent()) {
            metadataField = metadataFieldPage.getContent();
        }
        return metadataField;
    }

    public Long addCategory(String name, Long parentId) {
        if (categoryRepo.findByName(name) != null)
            throw new DataAlreadyPresentException("Category with this name is already present");
        Category category = new Category();
        category.setName(name);
        log.info(category.getName());
        System.out.println(parentId);
        if (parentId != null) {
            Category parentCategory = categoryRepo.getById(parentId);
            System.out.println(parentCategory);
            category.setParentCategory(parentCategory);
            /*            parentCategory.setId(parentId);*/
            System.out.println("Parent category ki id hai " + category.getParentCategory().getId());
            /*category.setParentCategory(parentCategory);*/
        }
        categoryRepo.save(category);
        category = categoryRepo.findByName(name);
        Long id = category.getId();
        return id;
    }

    //parent tk ka kaise krenge oata krna hai
    public CategoryResponseTO getCategory(Long id) {
        Category category = categoryRepo.getById(id);
        if (category == null)
            throw new EntityNotFoundException("Category with this Id cannot be found");
        CategoryResponseTO categories = CategoryResponseTO.mapper(category);
        return categories;
    }

    public List<CategoryResponseTO> getAllCategory(Pageable pageable) {
        List<CategoryResponseTO> categories = new ArrayList<>();
        Page<Category> categoryPage = categoryRepo.findAll(pageable);
        if (categoryPage.hasContent()) {
            categoryPage.getContent().forEach(category -> {
                CategoryResponseTO categoryResponseTO = CategoryResponseTO.mapper(category);
                categories.add(categoryResponseTO);
            });
        }
        return categories;
    }

    public Category updateCategory(Long id, String name) {
        Category category = categoryRepo.getById(id);
        if (category == null)
            throw new EntityNotFoundException("Category with this id not found");
        category.setName(name);
        return categoryRepo.save(category);
    }

    public CategoryMetadataFieldValues addMetadataCategory(Long categoryId, Long fieldId, String value) {
        Category category = categoryRepo.getById(categoryId);
        if (category == null)
            throw new EntityNotFoundException("Category with this id cannot be found\nError in mapping");
        CategoryMetadataField metadataField = metadataRepo.getById(fieldId);
        if (metadataField == null)
            throw new EntityNotFoundException("Field with this id cannot be found\nError in mapping");
        CategoryMetadataFieldValues metadataFieldValues = new CategoryMetadataFieldValues();
        metadataFieldValues.setCategoryMetadataField(metadataField);
        metadataFieldValues.setCategory(category);
        Set<String> values = new HashSet<>();
        String[] words = value.split(",");
        for (String word : words
        ) {
            values.add(word);
        }
        metadataFieldValues.setCategoryValues(values);
        System.out.println(metadataFieldValues);
        metadataFieldValuesRepo.save(metadataFieldValues);
        return metadataFieldValues;
    }

    public List<SellerCategoryResponseTO> getAllCategories() {
        List<SellerCategoryResponseTO> responseTO = new ArrayList<>();
        List<Category> categories = categoryRepo.findAll();
        log.info("Got all categories");
        System.out.println(categories);
        List<Category> leafCategories = new ArrayList<>();
        categories.stream().forEach(category -> {
            Long id = category.getId();
            Long count = categoryRepo.countByParentCategoryId(id);
            if (count == 0) {
                leafCategories.add(category);
                List<CategoryMetadataFieldValues> fieldValues = category.getCategoryMetadataFieldValues();
                fieldValues.stream().forEach(metadataFieldValues ->
                {
                    CategoryMetadataField metadataField = metadataFieldValues.getCategoryMetadataField();
                    Set<String> values = metadataFieldValues.getCategoryValues();
                    String parentNode = category.getParentCategory().getName();
                    SellerCategoryResponseTO response = SellerCategoryResponseTO.mapper(category, metadataField, values, parentNode);
                    responseTO.add(response);
                });
                log.info("Each leaf category");
            }
        });
        return responseTO;
    }

    public List<SellerCategoryTO> getCategoryForCustomer(Long id) {
        List<SellerCategoryTO> response = new ArrayList<>();
        Category category = categoryRepo.getById(id);
        if (category == null)
            throw new EntityNotFoundException("No category found with this categoryId");
        Long parentId = category.getParentCategory().getId();
        List<Category> categoryList = categoryRepo.findByParentCategoryId(parentId);
        System.out.println("Parent wala");
        System.out.println(categoryList);
        System.out.println("khatam parent wala");
        categoryList.stream().forEach(category1 -> {
            response.add(SellerCategoryTO.mapper(category1));
        });
        return response;
    }

    public FilterDetailsForCustomerTO fetchFiler(Long categoryId)
    {
        FilterDetailsForCustomerTO filterDetails = new FilterDetailsForCustomerTO();
        List<SellerCategoryResponseTO> response =  new ArrayList<>();
        List<String> brands = new ArrayList<>();
        Category category = categoryRepo.getById(categoryId);
        List<CategoryMetadataFieldValues> metadataFieldValues1 = category.getCategoryMetadataFieldValues();
        metadataFieldValues1.stream().forEach(metadataFieldValues -> {
            CategoryMetadataField metadataField = metadataFieldValues.getCategoryMetadataField();
            Set<String> values = metadataFieldValues.getCategoryValues();
           response.add(SellerCategoryResponseTO.mapper(category,metadataField,values,category.getParentCategory().getName()));
        });
        filterDetails.setSellerCategoryResponseTO(response);
        List<Product> productList = category.getProduct();
        for (Product products:productList
             ) {
            brands.add(products.getBrand());
        }
        filterDetails.setBrands(brands);
        return filterDetails;
    }

    public void updateMetadataValue(String values,Long categoryId,Long fieldId)
    {
        Category category = categoryRepo.getById(categoryId);
        CategoryMetadataField metadataField = new CategoryMetadataField();
        List<CategoryMetadataFieldValues> fieldValues = category.getCategoryMetadataFieldValues();
        fieldValues.forEach(metadataFieldValues -> {
           CategoryMetadataField metadataField1 = metadataFieldValues.getCategoryMetadataField();
            if (fieldId == metadataField1.getId()) {
                Set<String> setValue = metadataFieldValues.getCategoryValues();
                String[] words = values.split(",");
                for (String word : words
                ) {
                    setValue.add(word);
                }
                metadataFieldValues.setCategoryValues(setValue);
                metadataFieldValuesRepo.save(metadataFieldValues);
            }else {
                throw new EntityNotFoundException("There is no relation between these categoryId and fieldId So cannot update");
            }
        });
    }
}
