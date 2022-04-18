package com.bootcampproject.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.bootcampproject.dto.*;
import com.bootcampproject.entities.Product;
import com.bootcampproject.entities.ProductVariation;
import com.bootcampproject.exceptions.DataNotUpdatedException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.exceptions.NoEntityFoundException;
import com.bootcampproject.services.CategoryService;
import com.bootcampproject.services.ProductService;
import com.bootcampproject.services.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@PreAuthorize("hasRole('ROLE_SELLER')")
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //<<<<<<<<<<<<<<<<<<<<<-----------------------------View Profile of Seller---------------------->>>>>>>>>>>>>>>>>
    @GetMapping("/profile")
    public ResponseEntity<?> getDetails() {
        return new ResponseEntity<AdminSellerResponseTO>(sellerService.getDetails(), HttpStatus.OK);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<--------------------------Update Details of the Seller---------------------------->>>>>>>>>>>>>>>>>>>
    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody SellerUpdateTO seller, BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getAllErrors().stream().map(objectError -> {
                return objectError.getDefaultMessage();
            }).collect(Collectors.joining("\n"));
            log.warn("Validation failed: " + error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {
                return objectError.getDefaultMessage();
            }).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            sellerService.updateDetails(seller);
            return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
        } catch (DataNotUpdatedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error in updating details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<----------------------Update password for Seller------------------.>>>>>>>.>>>>>>>>>>
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordTO updatePasswordTO, BindingResult result) {
       //Put
        if (result.hasErrors()) {
            String error = result.getAllErrors().stream().map(objectError -> {
                return objectError.getDefaultMessage();
            }).collect(Collectors.joining("\n"));
            log.warn("Validation failed: " + error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {
                return objectError.getDefaultMessage();
            }).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        if (!Objects.equals(updatePasswordTO.getPassword(), updatePasswordTO.getConfirmPassword()))
            return new ResponseEntity<String>("Password and Confirm Password does not match", HttpStatus.BAD_REQUEST);
        sellerService.updatePassword(updatePasswordTO.getPassword());
        return new ResponseEntity<String>("Password changed Successfully", HttpStatus.OK);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<,---------------------Updating address of a seller--------------------->>>>>>>>>>>>>>>>>>>>
    @PutMapping("/updateAddress/{address_id}")
    public ResponseEntity<?> updateAddress(@RequestBody AddressUpdateTO address, @PathVariable("address_id") Long address_id) {
        try {
            sellerService.updateAddress(address, address_id);
            return new ResponseEntity<String>("Address Updated", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception occurred while updating address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<------------------------Product API's--------------------->>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<<<<------------------_Add a product ------------------------->>>>>>>>>>>>>>>>>>>>>>>>>

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductEnterTO product) {
        try {
            productService.addProduct(product);
            return new ResponseEntity<String>("Product Saved Successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error("Cannot find category with this user id" + e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception in" + e);
            return new ResponseEntity<>("Exception while saving product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------Add a product variation--------------------->>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/addproductvariation")
    public ResponseEntity<?> addProductVariation(@Valid @RequestBody ProductVariationTO product) {
      /*      product.setMetadata(headers.toSingleValueMap());
            System.out.println("Map is "+headers.toSingleValueMap());*/
            return new ResponseEntity<ProductVariationTO>(productService.addProductVariation(product), HttpStatus.OK);

    }


//<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------View Product from product id------------------->>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/viewproduct/{product_id}")
    public ResponseEntity<?> viewProduct(@PathVariable(name = "product_id")Long product_id)
    {
        try
        {
            return new ResponseEntity<ProductTO>(productService.viewProductSeller(product_id),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing a product",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,-------------------View Product variation by variation id----------------->>>>>>>>>>>>>>>>>>
    @GetMapping("/viewProductVariation/{variation_id}")
    public ResponseEntity<?> viewProductVariation(@PathVariable("variation_id")Long variation_id)
    {
        try {
            return new ResponseEntity<ProductVariationTO>(productService.viewProductVariation(variation_id),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing the Product Variation",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<<<<--------------------_View All products by pagination--------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/viewproducts")
    public ResponseEntity<?> viewAllProduct(@RequestParam(name = "offset",required = false,defaultValue = "0")Integer offset,@RequestParam(name = "limit",required = false,defaultValue = "2")Integer limit)
    {
        try {
            List<ProductTO> products = productService.viewAllProduct(PageRequest.of(offset,limit));
            return new ResponseEntity<List<ProductTO>>(products,HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            log.error("Exception in "+e);
            return new ResponseEntity<String>("Exception occurred while viewing all products",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewvariations")
    public ResponseEntity<?> viewAllVariations(@RequestParam(name = "offset",required = false,defaultValue = "0")Integer offset,@RequestParam(name = "limit",required = false,defaultValue = "2")Integer limit)
    {
        try {
            List<ProductVariationTO>variations = productService.viewAllVariations(PageRequest.of(offset,limit));
            return new ResponseEntity<List<ProductVariationTO>>(variations,HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            log.error("Exception in "+e);
            return new ResponseEntity<String>("Exception occurred while viewing all variations",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<_------------------------View all product Variation by variation id-------------------->>>>>>>>>>>>>>>
/*    @GetMapping("viewallproductvariation/{product_id}")
    public ResponseEntity<?> viewAllProductsVariation(@PathVariable(name = "product_id")Long product_id,@RequestParam(name = "offset",required = false)Integer offset,@RequestParam(name = "limit",required = false)Integer limit)
    {
        try {
            Page<ProductVariation> productVariations = productService.viewProductVariations(PageRequest.of(offset,limit),product_id);
            return new ResponseEntity<List<ProductVariation>>(productVariations.getContent(),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing product Variations",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

//<<<<<<<<<<<<<<<<<<<<<<<<-----------------------Delete a product by product id------------------.>>>>>>>>>>>>>>>>>>>>>>>>>>
    @DeleteMapping("/product/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "product_id")Long product_id)
    {
        try {
            return new ResponseEntity<String>(productService.deleteProduct(product_id),HttpStatus.OK);
        }catch (NoEntityFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while deleting the product",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<--------------------------Update a product by product id----------------------->>>>>>>>>>>>>>>>>>>>>>>>
    @PutMapping("/updateproduct/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "product_id")Long product_id,@RequestBody UpdateProductTO productTO)
    {
        try {
            productService.updateProduct(product_id,productTO);
            return new ResponseEntity<String>("Product Updated Successfully",HttpStatus.OK);
        }catch (EntityNotFoundException | DataNotUpdatedException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<String>("Exception occurred while updating the address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<,-------------------Update Variation by variation id---------------------->>>>>>>>>>>>>>>
    @PutMapping("updateproductvariation/{variation_id}")
    public ResponseEntity<?> updateProductVariation(@PathVariable(name = "variation_id")Long variation_id,@Valid @RequestBody UpdateProductVariationTO variationTO)
    {
        try {
            productService.updateProductVariation(variation_id,variationTO);
            return new ResponseEntity<String>("Variation details updated",HttpStatus.OK);
        }catch (EntityNotFoundException | DataNotUpdatedException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<String>("Exception occurred while updating the address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//<<<<<<<<<<<<<<<<<<<----------------------Get all Categories-------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories()
    {
        return new ResponseEntity<List<SellerCategoryResponseTO>>(categoryService.getAllCategories(),HttpStatus.OK);
    }

}