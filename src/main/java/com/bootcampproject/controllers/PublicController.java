package com.bootcampproject.controllers;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.services.CustomerService;
import com.bootcampproject.services.SellerService;
import com.bootcampproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PublicController {

    @Value("${customer.activation.time.duration}")
    private Long duration;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SellerService sellerService;

    @PostMapping("/register/seller")
    public ResponseEntity<?> createSeller(@Valid @RequestBody SellerTO sellerTO,BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            if (!Objects.equals(sellerTO.getPassword(),sellerTO.getConfirmPassword())) {
                return new ResponseEntity<String>("Password does not match", HttpStatus.BAD_REQUEST);
            }
            SellerTO seller = sellerService.createSeller(sellerTO);
            System.out.println(seller);
            return new ResponseEntity<SellerTO>(seller, HttpStatus.OK);
        }
        catch(UserAlreadyExistException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            log.error(" Exception occurred in register seller " + sellerTO, e);
            return new ResponseEntity<String>("Exception occurred while registering customer",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/register/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerTO customerTO,BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {

            if (!Objects.equals(customerTO.getPassword(), customerTO.getConfirmPassword())) {
                return new ResponseEntity<String>("Password does not match", HttpStatus.BAD_REQUEST);
            }
               CustomerTO customer = customerService.createCustomer(customerTO);
            System.out.println(customer);
            return new ResponseEntity<CustomerTO>(customer, HttpStatus.OK);
        }
        catch(UserAlreadyExistException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            log.error(" Exception occurred in register customer " + customerTO, e);
            return new ResponseEntity<String>("Exception occurred while registering customer",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //refacottor
    @GetMapping("/activate/{uuid}")
    public ResponseEntity<?> activateUser( @PathVariable("uuid") String uuid) {
        log.info("activation happened");
        System.out.println(uuid);
        Customer customer = customerService.findCustomerByToken(uuid);
        if(customer==null)
        {
            log.info("activation failed");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
/*        log.info(String.valueOf(user.getUuid()));*/
        log.info("uuid of user found "+customer.getActivationToken());
        Date date1 = customer.getActivationTokenAt();
        Date date2= new Date();
        Long time = date2.getTime()-date1.getTime();
        long diffSeconds = time / 1000 % 60;
        long diffMinutes = time / (60 * 1000) % 60;
        log.info("time difference "+ time);
        log.info("time in seconds "+ diffSeconds);
        log.info("time in minutes "+ diffMinutes);
/*        if (!Objects.equals(customer.getActivationToken(),uuid))
        *//*if (!user.getUuid().equals(uuid))*//*
        {
            log.info("activation failed due to mismatch of uuid");
            return new ResponseEntity<String>("UUID NOT MATCHED",HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
        log.info("activation performed");
        if(diffSeconds>duration)
        {
            return new ResponseEntity<String>("Time over for activation please check mail again",HttpStatus.FORBIDDEN);
        }
        User user = userService.findUserById(customer.getId());
        userService.activateUser(user);
/*        userService.mailUser(user);*/
        return new ResponseEntity<String>("Account Activated", HttpStatus.OK);
    }
    @GetMapping("/i18n")
    public String i18n()
    {
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());

    }
}