package com.bootcampproject.controllers;

import com.bootcampproject.dto.*;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Product;
import com.bootcampproject.exceptions.DataNotUpdatedException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.exceptions.NoEntityFoundException;
import com.bootcampproject.repositories.CategoryRepo;
import com.bootcampproject.services.CategoryService;
import com.bootcampproject.services.CustomerService;
import com.bootcampproject.services.ProductService;
import com.bootcampproject.services.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

//<<<<<<<<<<<<<<<<<<<-----------------View profile of customer---------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/profile")
    public ResponseEntity<?> getDetails()
    {
        return new ResponseEntity<CustomerResponseTO>(customerService.getDetails(), HttpStatus.OK);
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<-----------------------View Address of Customer-------------------->>>>>>>>>>>>>>>
    @GetMapping("/address")
    public ResponseEntity<?> getAddressDetails() {
        try {
            return new ResponseEntity<List<Address>>(customerService.getAddressDetails(), HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<-----------------------Update Customer Details--------------->>>>>>>>>>>>>>>>>>>>>>
    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody CustomerUpdateTO customerTO, BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            customerService.updateDetails(customerTO);
            return new ResponseEntity<String>("Details updated",HttpStatus.OK);
        }
        catch (DataNotUpdatedException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>("Error in updating details",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//<<<<<<<<<<<<<<<<<<<<<<----------------------Update Password API---------------------->>>>>>>>>>>>>>>>>>>>
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

//<<<<<<<<<<<<<<<<<<<<<<<<<<<_-----------------------Add an Address to Customer--------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@Valid @RequestBody Address address,BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        customerService.addAddress(address);
        return new ResponseEntity<String>("Address updated",HttpStatus.OK);
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<_-----------------------Delete an Address of Customer---------------------->>>>>>>>>>>>>>>>>>
    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id)
    {
       Integer flag = customerService.deleteAddress(id);
       if (flag == 1)
           return new ResponseEntity<String>("Address with this id is not found\nPlease check it again",HttpStatus.BAD_REQUEST);
       return new ResponseEntity<String>("Address deleted Successfully",HttpStatus.OK);
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-----------------------Update an Address------------------------------>>>>>>>>>>>>>>>>>>>>>>>>
    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable("id")Long id,@Valid @RequestBody AddressUpdateTO address)
    {
        Integer flag = customerService.updateAddress(id,address);
        if (flag == 0)
            return new ResponseEntity<String>("Address with given id cannot be found",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Address updated Successfully",HttpStatus.OK);
    }

//<<<<<<<<<<<<<<<<<<<<<<<<_-----------------------------Product API's----------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>

//<<<<<<<<<<<<<<<<<<<<<<<<<------------------------View a product with product id---------------------->>>>>>>>>>>>>>>>>>>>
    @GetMapping("viewproduct/{product_id}")
    public ResponseEntity<?> viewProduct(@PathVariable(name = "product_id")Long product_id)
    {
        try
        {
            return new ResponseEntity<CustomerProductResponseTO>(productService.viewProductCustomer(product_id),HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing a product",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<----------------------To list All Products------------------------------------>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "offset", required = false,defaultValue = "0") Integer offset, @RequestParam(name = "limit", required = false,defaultValue = "5") Integer limit)
    {
        List<CustomerProductResponseTO> products = productService.viewAllProductForCustomer(PageRequest.of(offset,limit));
        return new ResponseEntity<List<CustomerProductResponseTO>>(products,HttpStatus.OK);
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<------------------_To list similar products on basis of category--------------->>>>>>>>>>>>>>>>>>>>
    @GetMapping("/similarproducts/{product_id}")
    public ResponseEntity<?> getSimilarProduct(@PathVariable(name = "product_id")Long product_id)
    {
        return new ResponseEntity<List<CustomerProductResponseTO>>(productService.viewSimilarProducts(product_id),HttpStatus.OK);
    }


//<<<<<<<<<<<<<<<<<-------------------------get Categories based on id or All categories can be showed----------------->>>>>>>>>>>>>>>>>
    @GetMapping("/categories")
    public ResponseEntity<?> viewCategories(@RequestParam(name = "categoryId",required = false)Long id) {
        try {
            if (id != null) {
                return new ResponseEntity<List<SellerCategoryTO>>(categoryService.getCategoryForCustomer(id),HttpStatus.OK);
            }
            return new ResponseEntity<String>("Hello",HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            log.warn("Id not found "+e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//<<<<<<<<<<<<<<<<<------------------------finding filtered details of a category----------------->>>>>>>>>>>>>>>>>>>>

    @GetMapping("/filtercategory")
    public ResponseEntity<?> filterCategory(@RequestParam(name = "categoryId")Long categoryId)
    {
        return new ResponseEntity<FilterDetailsForCustomerTO>(categoryService.fetchFiler(categoryId),HttpStatus.OK);
    }
}
