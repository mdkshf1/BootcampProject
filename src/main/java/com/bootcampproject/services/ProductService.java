package com.bootcampproject.services;

import com.bootcampproject.dto.UpdateProductTO;
import com.bootcampproject.dto.UpdateProductVariationTO;
import com.bootcampproject.entities.Category;
import com.bootcampproject.entities.Product;
import com.bootcampproject.entities.ProductVariation;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.repositories.ProductRepo;
import com.bootcampproject.repositories.ProductVariationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductVariationRepo variationRepo;


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

    public Product viewProductSeller(Long id)
    {
        Product product = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("Product with this id cannot be found");
        return product;
    }

    public ProductVariation viewProductVariation(Long id)
    {
        ProductVariation productVariation = variationRepo.getById(id);
        if (productVariation == null)
            throw new EntityNotFoundException("Product Variaion with this id cannot be found");
        return productVariation;
    }

    public Page<Product> viewAllProduct(Pageable pageable)
    {
        Page<Product> products = productRepo.findAll(pageable);
        if (products.isEmpty())
            throw new EntityNotFoundException("No product found");
        return products;
    }

    public Page<ProductVariation> viewProductVariations(Pageable pageable,Long id)
    {
        Product product = productRepo.getById(id);
        List<ProductVariation> productVariations = product.getProductVariation();
        Page<ProductVariation> productVariationPage = new PageImpl<ProductVariation>(productVariations,pageable,productVariations.size());
        return productVariationPage;
    }

    public String deleteProduct(Long id)
    {
        Product product = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("Product with this id cannot be found");
        product.setIsDeleted(true);
        productRepo.save(product);
        return "Product Deleted Successfully";
    }
    public void updateProduct(Long id, UpdateProductTO productTO)
    {
        Product product  = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("No Product found with this product id");
        if (productTO.getId() != null)
            throw new CannotChangeException("You cannot update your id");
        if (productTO.getBrand() != null)
            throw new CannotChangeException("You canot change the brand of your product");
        if (productTO.getIsActive() != null)
            product.setIsActive(productTO.getIsActive());
        if (productTO.getName() != null)
            product.setName(productTO.getName());
        if (productTO.getDescription() != null)
            product.setDescription(productTO.getDescription());
        if (productTO.getIsCancellable() != null)
            product.setIsCancellable(productTO.getIsCancellable());
        if (productTO.getIsReturnable() != null)
            product.setIsReturnable(productTO.getIsReturnable());
        if (productTO.getIsDeleted() != null)
            throw new CannotChangeException("You cannot change the deleted partto inactive please give false in isActive part");
        productRepo.save(product);
    }

    public void updateProductVariation(Long id, UpdateProductVariationTO variationTO)
    {
        ProductVariation variation = variationRepo.getById(id);
        if (variation == null)
            throw new EntityNotFoundException("Product Variation with this id cannot be found");
        if (variationTO.getId() != null)
            throw new CannotChangeException("You cannot change the id for a variation");
        if (variationTO.getQuantityAvailable() != null)
            variation.setQuantityAvailable(variationTO.getQuantityAvailable());
        if (variationTO.getPrice() != null)
            variation.setPrice(variationTO.getPrice());
        if (variationTO.getMetadata() != null)
            variation.setMetadata(variationTO.getMetadata());
        if (variationTO.getPrimaryImageName() != null)
            variation.setPrimaryImageName(variationTO.getPrimaryImageName());
        if (variationTO.getIsActive() != null)
            variation.setIsActive(variationTO.getIsActive());
        variationRepo.save(variation);
    }

    public Product viewProductCustomer(Long id)
    {
        Product product = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("Product with this id cannot be found");
        return product;
    }
}
