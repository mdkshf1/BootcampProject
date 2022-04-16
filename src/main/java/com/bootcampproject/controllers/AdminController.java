package com.bootcampproject.controllers;

import com.bootcampproject.dto.AdminCustomerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
/*@PreAuthorize("hasRole('ROLE_ADMIN')")*/
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomer(@RequestHeader(name = "email", required = false) String email, @RequestHeader(name = "offset", required = false) Integer offset, @RequestHeader(name = "limit", required = false) Integer limit) {
        //instead of pagesize use limit
        if (email != null)
            return new ResponseEntity<AdminCustomerResponseTO>(adminService.findCustomerByEmail(email), HttpStatus.OK);
        return new ResponseEntity<Page<AdminCustomerResponseTO>>((Page<AdminCustomerResponseTO>) adminService.findAllCustomers(PageRequest.of(offset, limit, Sort.by("id"))).getContent(), HttpStatus.OK);
    }

    @GetMapping("/sellers")
    public ResponseEntity<?> getSeller(@RequestHeader(name = "email",required = false)String email,@RequestHeader(name = "offset",required = false)Integer offset,@RequestHeader(name = "limit",required = false)Integer limit)
    {
        if (email != null)
            return new ResponseEntity<SellerResponseTO>(adminService.findSellerByEmail(email),HttpStatus.OK);
        return new ResponseEntity<Page<SellerResponseTO>>((Page<SellerResponseTO>) adminService.findAllSellers(PageRequest.of(offset,limit,Sort.by("id"))).getContent(),HttpStatus.OK);
    }

/*    @PatchMapping("/activateCustomer/{user_id}")
    public ResponseEntity<?> activateCustomer(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.activateCustomer(user_id),HttpStatus.OK);
    }

    @PatchMapping("/deactivateCustomer/{user_id}")
    public ResponseEntity<?> deactivateCustomer(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.deactivateCustomer(user_id),HttpStatus.OK);
    }*/

    @PatchMapping("/activateOrDeactivateCustomer/{user_id}")
    public ResponseEntity<?> activateOrDeactivateCustomer(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.activateOrDeactivateCustomer(user_id),HttpStatus.OK);
    }

    @PatchMapping("/activateOrDeactivateSeller/{user_id}")
    public ResponseEntity<?> activateOrDeactivateSeller(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.activateOrDeactivateSeller(user_id),HttpStatus.OK);
    }
/*
    @PatchMapping("/activateSeller/{user_id}")
    public ResponseEntity<?> activateSeller(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.activateSeller(user_id),HttpStatus.OK);
    }

    @PatchMapping("/deactivateSeller/{user_id}")
    public ResponseEntity<?> deactivateSeller(@PathVariable("user_id")Long user_id)
    {
        return new ResponseEntity<String>(adminService.deactivateSeller(user_id),HttpStatus.OK);
    }*/
}
