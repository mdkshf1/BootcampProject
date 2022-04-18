package com.bootcampproject.services;

import com.bootcampproject.dto.*;
import com.bootcampproject.entities.*;
import com.bootcampproject.exceptions.DataNotUpdatedException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.repositories.CategoryRepo;
import com.bootcampproject.repositories.ProductRepo;
import com.bootcampproject.repositories.ProductVariationRepo;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductVariationRepo variationRepo;


    public void addProduct(ProductEnterTO productTO)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userRepo.findByEmail(email);
        Seller seller = user.getSeller();
        Product product = new Product();
        product.setName(productTO.getName());
        product.setDescription(productTO.getDescription());
        Category category = categoryRepo.getById(productTO.getCategoryId());
        if (category == null)
            throw new EntityNotFoundException("Cannot find Category with this id");
        product.setCategory(category);
        product.setIsCancellable(productTO.getIsCancellable());
        product.setIsReturnable(product.getIsReturnable());
        product.setBrand(productTO.getBrand());
        product.setSeller(seller);
        System.out.println(product);
        productRepo.save(product);
    }

    public ProductTO viewProductSeller(Long id)
    {
        Product product = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("Product with this id cannot be found");
        return ProductTO.mapper(product);
    }

/*    public ProductVariation viewProductVariation(Long id)
    {
        ProductVariation productVariation = variationRepo.getById(id);
        if (productVariation == null)
            throw new EntityNotFoundException("Product Variaion with this id cannot be found");
        return productVariation;
    }*/

    public List<ProductTO> viewAllProduct(Pageable pageable)
    {
        List<ProductTO> products = new ArrayList<>();
        Page<Product> productPage = productRepo.findAll(pageable);
        if (productPage.hasContent())
        {
            productPage.getContent().forEach(product -> {
                ProductTO product1 = ProductTO.mapper(product);
                products.add(product1);
            });
        }
        return products;
    }

    public List<ProductVariationTO> viewAllVariations(Pageable pageable)
    {
        List<ProductVariationTO> variations = new ArrayList<>();
        Page<ProductVariation> variationPage = variationRepo.findAll(pageable);
        if (variationPage.hasContent())
        {
            variationPage.getContent().forEach(productVariation -> {
                ProductVariationTO productVariationTO = ProductVariationTO.mapper(productVariation);
                variations.add(productVariationTO);
            });
        }
        return variations;
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
            throw new DataNotUpdatedException("You cannot update your id");
        if (productTO.getBrand() != null)
            throw new DataNotUpdatedException("You canot change the brand of your product");
        if (productTO.getName() != null)
            product.setName(productTO.getName());
        if (productTO.getDescription() != null)
            product.setDescription(productTO.getDescription());
        if (productTO.getIsCancellable() != null)
            product.setIsCancellable(productTO.getIsCancellable());
        if (productTO.getIsReturnable() != null)
            product.setIsReturnable(productTO.getIsReturnable());
        productRepo.save(product);
    }

    public void updateProductVariation(Long id, UpdateProductVariationTO variationTO)
    {
        ProductVariation variation = variationRepo.getById(id);
        if (variation == null)
            throw new EntityNotFoundException("Product Variation with this id cannot be found");
        if (variationTO.getId() != null)
            throw new DataNotUpdatedException("You cannot change the id for a variation");
        if (variationTO.getQuantityAvailable() != null)
            variation.setQuantityAvailable(Math.toIntExact((Long) variationTO.getQuantityAvailable()));
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

    public CustomerProductResponseTO viewProductCustomer(Long id)
    {
        Product product = productRepo.getById(id);
        if (product == null)
            throw new EntityNotFoundException("Product with this id cannot be found");
        return CustomerProductResponseTO.mapper(product);
    }

    public List<CustomerProductResponseTO> viewAllProductForCustomer(Pageable pageable)
    {
        List<CustomerProductResponseTO> products = new ArrayList<>();
        Page<Product> productPage = productRepo.findAll(pageable);
        if (productPage.hasContent())
        {
            productPage.getContent().forEach(product -> {
                CustomerProductResponseTO product1 = CustomerProductResponseTO.mapper(product);
                products.add(product1);
            });
        }
        return products;
    }

    public List<CustomerProductResponseTO> viewSimilarProducts(Long product_id)
    {
        List<CustomerProductResponseTO> response = new ArrayList<>();
        Product product = productRepo.getById(product_id);
        Category category = product.getCategory();
        Category parentCategory = category.getParentCategory();
        List<Product> productList = category.getProduct();
        List<Product> productList1 = parentCategory.getProduct();
        productList.forEach(product1 -> {
            response.add(CustomerProductResponseTO.mapper(product1));
        });
        productList1.forEach(product1 -> {
            response.add(CustomerProductResponseTO.mapper(product1));
        });
        return response;
    }

    public String activateOrDeactivateProduct(Long product_id, Boolean status) {
        Product product = productRepo.getById(product_id);
        if (product == null)
            throw new EntityNotFoundException("Product with this product id not found");
        if (product.getIsActive() == status && status == true)
            return "Product Already activated";
        else if (product.getIsActive() == status && status == false)
            return "Product Already deactivated";
        product.setIsActive(status);
        productRepo.save(product);
        if (product.getIsActive())
            return "Product was Deactivated and now activated";
        return "Product was activated and now deactivated";
    }
    public ProductVariationTO addProductVariation(ProductVariationTO productVariationTO)  {
        ProductVariation productVariation = new ProductVariation();
        productVariation.setProduct(productRepo.getById(productVariationTO.getProductId()));
        productVariation.setQuantityAvailable(Math.toIntExact((long) productVariationTO.getQuantityAvailable()));
        productVariation.setPrice(productVariationTO.getPrice());
        productVariation.setMetadata(productVariationTO.getMetadata());
        productVariation.setPrimaryImageName(productVariationTO.getPrimaryImageName());
        variationRepo.save(productVariation);
        return productVariationTO;
    }
    public ProductVariationTO viewProductVariation(Long id)
    {
        ProductVariation productVariation = variationRepo.getById(id);
        return ProductVariationTO.mapper(productVariation);
    }


}
