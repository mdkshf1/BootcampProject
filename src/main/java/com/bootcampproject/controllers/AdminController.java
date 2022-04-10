package com.bootcampproject.controllers;

import com.bootcampproject.dto.CustomerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.services.AdminService;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomer(@RequestHeader(name = "email",required = false)String email)
    {
        //instead of pagesize use limit
/*        if(email!=null)
        {
            //findCustomerByEmail
            //inside customerService
            //Implement pagination on controller

        }*/
        log.info("Current login User"+ SecurityContextHolderUtil.getCurrentUserEmail());
        return new ResponseEntity<CustomerResponseTO>(adminService.findCustomerByEmail(email), HttpStatus.OK);
            /*return new ResponseEntity<List<CustomerResponseTO>>(adminService.getAllCustomer(),HttpStatus.OK);*/
    }
/*
    @GetMapping("sellers")
    public ResponseEntity<?> getSeller(@RequestHeader(name = "email",required = false)String email)
    {
        if (email!=null)
        {
            return new ResponseEntity<SellerResponseTO>(adminService.getSingleSeller(email),HttpStatus.OK);
        }
            return new ResponseEntity<List<SellerResponseTO>>(adminService.)
    }*/

}
