package com.bootcampproject.controllers;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("/seller")
    public ResponseEntity<?> createSeller(@Valid @RequestBody UserTO userTO) {
        if (userTO.getPassword().equals(userTO.getConfirmPassword())){
            UserTO user = userService.createSeller(userTO);
            return new ResponseEntity<UserTO>(userTO,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<String>("Password not matched",HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody UserTO userTO, BindingResult bindingResult) {
        if (userTO.getPassword().equals(userTO.getConfirmPassword())){
            UserTO user = userService.createCustomer(userTO);
            return new ResponseEntity<UserTO>(userTO,HttpStatus.OK);
        }
        else if (!userTO.getPassword().equals(userTO.getConfirmPassword()))
        {
            return new ResponseEntity<String>("Password and confirm password does not match",HttpStatus.EXPECTATION_FAILED);
        }
        else
            return new ResponseEntity<String>("Any other error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}