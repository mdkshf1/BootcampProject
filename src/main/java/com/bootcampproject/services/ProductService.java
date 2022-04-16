package com.bootcampproject.services;

import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.Product;
import com.bootcampproject.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product addProduct(Product product)
    {
        Product finalProduct = new Product();
        finalProduct.setName(product.getName());
        finalProduct.setBrand(product.getBrand());
        Category category = product.getCategory();
        Category finalCategory = new Category();
        finalCategory.setId(category.getId());
        finalCategory.setProduct(product);
        finalCategory.setName(category.getName());
        finalCategory.setCategoryMetadataFieldValues(category.getCategoryMetadataFieldValues());
        finalProduct.setCategory(finalCategory);
        finalProduct.setDescription(product.getDescription());
        finalProduct.setIsCancellable(product.getIsCancellable());
        finalProduct.setIsReturnable(product.getIsReturnable());
        finalProduct.setIsActive(false);
        finalProduct.setIsDeleted(false);
        return finalProduct;
    }
}
