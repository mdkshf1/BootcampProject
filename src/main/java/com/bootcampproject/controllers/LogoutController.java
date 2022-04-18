package com.bootcampproject.controllers;


import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.services.UserService;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;




@RestController
public class LogoutController {
    @Autowired
    private UserService userService;
//<<<<<<<<<<<<<<<<-----------------------Logout API--------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @PostMapping("/doLogout")
    public ResponseEntity<?> logOut(HttpServletRequest request) throws ServletException {
        try {
            return new ResponseEntity<String>(userService.logOut(request.getHeader("Authorization")), HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occurred while logging out",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}