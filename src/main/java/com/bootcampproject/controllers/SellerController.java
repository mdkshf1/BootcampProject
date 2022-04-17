package com.bootcampproject.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.bootcampproject.dto.*;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Product;
import com.bootcampproject.entities.ProductVariation;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.exceptions.NoEntityFoundException;
import com.bootcampproject.services.ProductService;
import com.bootcampproject.services.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@PreAuthorize("hasRole('ROLE_SELLER')")
@RequestMapping("/seller")
public class SellerController
{
    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;


    @GetMapping("/profile")
    public ResponseEntity<?> getDetails()
    {
        return new ResponseEntity<AdminSellerResponseTO>(sellerService.getDetails(),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody SellerUpdateTO seller, BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            sellerService.updateDetails(seller);
            return new ResponseEntity<String>("Details Updated",HttpStatus.OK);
        }
        catch (CannotChangeException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>("Error in updating details",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordTO updatePasswordTO,BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        if (!Objects.equals(updatePasswordTO.getPassword(),updatePasswordTO.getConfirmPassword()))
            return new ResponseEntity<String>("Password and Confirm Password does not match",HttpStatus.BAD_REQUEST);
        sellerService.updatePassword(updatePasswordTO.getPassword());
        return new ResponseEntity<String>("Password changed Successfully",HttpStatus.OK);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressUpdateTO address, BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        sellerService.updateAddress(address);
        return new ResponseEntity<String>("Address Updated",HttpStatus.OK);
    }
    @GetMapping("/check")
    public String check()
    {
        return "Working Seller";
    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product)
    {
        return new ResponseEntity<Product>(productService.addProduct(product),HttpStatus.OK);
    }
    @PostMapping("/addproductvariation")
    public ResponseEntity<?> addProductVariation()
    {
        return null;
    }
    @GetMapping("/viewProduct/{product_id}")
    public ResponseEntity<?> viewProduct(@PathVariable(name = "product_id")Long product_id)
    {
        try
        {
            return new ResponseEntity<Product>(productService.viewProductSeller(product_id),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing a product",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/viewProductVariation/{variation_id}")
    public ResponseEntity<?> viewProductVariation(@PathVariable("variation_id")Long variation_id)
    {
        try {
            return new ResponseEntity<ProductVariation>(productService.viewProductVariation(variation_id),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing the Product Variation",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/viewproducts")
    public ResponseEntity<?> viewAllProduct(@RequestParam(name = "offset",required = false)Integer offset,@RequestParam(name = "limit",required = false)Integer limit)
    {
        try {
            Page<Product> products = productService.viewAllProduct(PageRequest.of(offset,limit));
            return new ResponseEntity<List<Product>>(products.getContent(),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing all products",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("viewallproductvariation/{product_id}")
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
    }
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
    @PutMapping("/updateproduct/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "product_id")Long product_id,@Valid @RequestBody UpdateProductTO productTO)
    {
        try {
            productService.updateProduct(product_id,productTO);
            return new ResponseEntity<String>("Address Updated Successfully",HttpStatus.OK);
        }catch (EntityNotFoundException | CannotChangeException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<String>("Exception occurred while updating the address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateproductvariation/{variation_id}")
    public ResponseEntity<?> updateProductVariation(@PathVariable(name = "variation_id")Long variation_id,@Valid @RequestBody UpdateProductVariationTO variationTO)
    {
        try {
            productService.updateProductVariation(variation_id,variationTO);
            return new ResponseEntity<String>("Variation details updated",HttpStatus.OK);
        }catch (EntityNotFoundException | CannotChangeException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<String>("Exception occurred while updating the address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}