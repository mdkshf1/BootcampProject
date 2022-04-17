package com.bootcampproject.controllers;

import com.bootcampproject.dto.*;
import com.bootcampproject.entities.Address;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.exceptions.NoEntityFoundException;
import com.bootcampproject.services.CustomerService;
import com.bootcampproject.services.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SellerService sellerService;

    @GetMapping("/profile")
    public ResponseEntity<?> getDetails()
    {
        return new ResponseEntity<CustomerResponseTO>(customerService.getDetails(), HttpStatus.OK);
    }

    @GetMapping("/address")
    public ResponseEntity<?> getAddressDetails() {
        try {
            return new ResponseEntity<List<Address>>(customerService.getAddressDetails(), HttpStatus.OK);
        }catch (NoEntityFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while viewing address",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Change it to a new transferable object
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
    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id)
    {
       Integer flag = customerService.deleteAddress(id);
       if (flag == 1)
           return new ResponseEntity<String>("Address with this id is not found\nPlease check it again",HttpStatus.BAD_REQUEST);
       return new ResponseEntity<String>("Address deleted Successfully",HttpStatus.OK);
    }

    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable("id")Long id,@Valid @RequestBody AddressUpdateTO address)
    {
        Integer flag = customerService.updateAddress(id,address);
        if (flag == 0)
            return new ResponseEntity<String>("Address with given id canot be found",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Address updated Successfully",HttpStatus.OK);
    }

    @GetMapping("/check")
    public String check()
    {
        return "Working";
    }
}
