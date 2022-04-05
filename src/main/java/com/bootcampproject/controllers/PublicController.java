package com.bootcampproject.controllers;

import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

/*    @PostMapping("/register/seller")
    public ResponseEntity<?> createSeller(@Valid @RequestBody SellerTO sellerTO, BindingResult result) {
        if(result.hasErrors())
        {
            *//*return new ResponseEntity<String>("validation failed",HttpStatus.BAD_REQUEST);*//*
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> { return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")),HttpStatus.BAD_REQUEST );
        }
        *//*Objects.equals(customerDto.getPassword(), customerDto.getConfirmPassword())*//*
        if (!Objects.equals(sellerTO.getPassword(),sellerTO.getConfirmPassword())){
            return new ResponseEntity<String>("Password not matched",HttpStatus.EXPECTATION_FAILED);
        }
        UserTO user = userService.createSeller(sellerTO);
        if(user==null)
        {
            return new ResponseEntity<String>("Email already used \n Please use another one",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<UserTO>(sellerTO,HttpStatus.OK);
    }*/

    @PostMapping("/register/seller")
    public ResponseEntity<?> createSeller(@Valid @RequestBody SellerTO sellerTO,BindingResult result)
    {
/*        if (result.hasErrors())
        {
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }*/
        if(!Objects.equals(sellerTO.getPassword(),sellerTO.getConfirmPassword()))
        {
            return new ResponseEntity<String>("Password not matched",HttpStatus.BAD_REQUEST);
        }
        SellerTO seller = userService.createSeller(sellerTO);
        if (seller==null)
            return new ResponseEntity<String>("Email already exist \n Please use another email",HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<SellerTO>(seller,HttpStatus.OK);
    }






    @PostMapping("/register/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody UserTO userTO, BindingResult bindingResult) {
        if (!userTO.getPassword().equals(userTO.getConfirmPassword()))
        {
            return new ResponseEntity<String>("Password and confirm password does not match",HttpStatus.EXPECTATION_FAILED);
        }
            UserTO user = userService.createCustomer(userTO);
            return new ResponseEntity<UserTO>(userTO,HttpStatus.OK);
            /*return new ResponseEntity<String>("Any other error",HttpStatus.INTERNAL_SERVER_ERROR);*/
    }


    @GetMapping("/activate/{uuid}")
    public ResponseEntity<?> activateUser( @PathVariable("uuid") String uuid) {
        log.info("activation happened");
        System.out.println(uuid);;
        User user = userService.checkUserByUuid(uuid);
        System.out.println("this is a user we found out " +user);
        if(user==null)
        {
            log.info("activation failed");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        log.info(String.valueOf(user.getUuid()));
        if (!Objects.equals(user.getUuid(),uuid))
        /*if (!user.getUuid().equals(uuid))*/
        {
            log.info("activation failed due to mismatch of uuid");
            return new ResponseEntity<String>("UUID NOT MATCHED",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("activation performed");
        userService.activateUser(user);
        userService.mailUser(user);
        return new ResponseEntity<String>("Account Activated", HttpStatus.OK);
    }
    @GetMapping("/i18n")
    public String i18n()
    {
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());

    }
}