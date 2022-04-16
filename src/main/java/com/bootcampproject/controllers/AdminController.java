package com.bootcampproject.controllers;

import com.bootcampproject.dto.AdminCustomerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.services.AdminService;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JdbcTokenStore tokenStore;

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

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request) throws ServletException {
       request.logout();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        String finalToken = token.replace("Bearer ","");
        tokenStore.setDeleteAccessTokenSql(finalToken);
       return new ResponseEntity<String>("Logged out",HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check()
    {
        return new ResponseEntity<String>("Working",HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public String currentUser()
    {
        return SecurityContextHolderUtil.getCurrentUserEmail();
    }
}
