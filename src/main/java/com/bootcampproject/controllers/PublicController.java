package com.bootcampproject.controllers;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("/seller")
    public UserTO createSeller(@Valid @RequestBody UserTO userTo)
    {
        return userService.createSeller(userTo);
    }

    @PostMapping("/customer")
    public UserTO createCustomer(@Valid @RequestBody UserTO userTO)
    {
        return userService.createCustomer(userTO);
    }

}
