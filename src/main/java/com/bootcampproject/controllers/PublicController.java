package com.bootcampproject.controllers;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.services.AdminService;
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
import java.util.Locale;
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
    private AdminService adminService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CustomerService customerService;


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
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            log.error(" Exception occurred in register seller " + sellerTO, e);
            return new ResponseEntity<String>("Exception occurred while registering customer",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("register/admin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody UserTO userTO, BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            if (!Objects.equals(userTO.getPassword(), userTO.getConfirmPassword())) {
                return new ResponseEntity<String>("Password does not match", HttpStatus.BAD_REQUEST);
            }
            User user1 = adminService.createAdmin(userTO);
            System.out.println(user1);
            return new ResponseEntity<String>("Admin created", HttpStatus.OK);
        }
        catch (UserAlreadyExistException e)
        {
            log.error(e.getMessage(),e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            log.error("Exception occured while creating admin");
            return new ResponseEntity<String>("Error occured while saving Admin",HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/activate/{token}")
    public ResponseEntity<?> activateUser( @PathVariable("token") String token) {
        Customer customer = customerService.findCustomerByToken(token);
        if(customer==null)
        {
            log.info("activation failed");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        log.info("uuid of user found "+customer.getActivationToken());
        Long diffMinutes =  customerService.findTime(customer.getActivationTokenAt(),new Date());
        if(diffMinutes>duration)
        {
            customerService.reactivateCustomer(customer);
            return new ResponseEntity<String>("Time over for activation please check mail again",HttpStatus.FORBIDDEN);
        }
        User user = userService.findUserById(customer.getId());
        userService.activateUser(user);
        return new ResponseEntity<String>("Account Activated", HttpStatus.OK);
    }

    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("email") String email)
    {
        User user = userService.findByEmail(email);
        if(user == null)
        {
            return new ResponseEntity<String>("User not found with this email",HttpStatus.BAD_REQUEST);
        }
        userService.forgotPassword(user);
        return new ResponseEntity<String>("Please check you Email to change your password",HttpStatus.OK);
    }
    @PutMapping("/change-password/{token}")
    public ResponseEntity<?> changePassword(@PathVariable("token") String token,@Valid @RequestBody User user)
    {
        User gotuser = userService.findUserByToken(token);
        if(gotuser == null)
        {
            return new ResponseEntity<String>("Token does not match please check the link and try again",HttpStatus.BAD_REQUEST);
        }
        String password = user.getPassword();
        user = userService.changePassword(gotuser,password);
        return new ResponseEntity<String>("Password changed Successfully",HttpStatus.OK);
    }

    @GetMapping("/i18n")
    public String i18n()
    {
        return messageSource.getMessage("good.morning.message",null,LocaleContextHolder.getLocale());
    }
}