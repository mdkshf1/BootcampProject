package com.bootcampproject.controllers;


import com.bootcampproject.dto.UserTO;
import com.bootcampproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public UserTO create(@Valid @RequestBody UserTO userTO)
    {
        return userService.create(userTO);
    }
}
