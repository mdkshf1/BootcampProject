package com.bootcampproject.controllers;

import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UpdatePasswordTO;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Product;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.services.ProductService;
import com.bootcampproject.services.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
        return new ResponseEntity<User>(sellerService.getDetails(),HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody SellerTO seller, BindingResult result)
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
    public ResponseEntity<?> updateAddress(@Valid @RequestBody Address address,BindingResult result)
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
    @PostMapping("addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product)
    {
        return new ResponseEntity<Product>(productService.addProduct(product),HttpStatus.OK);
    }
}