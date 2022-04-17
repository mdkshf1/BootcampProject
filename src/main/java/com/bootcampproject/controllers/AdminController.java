package com.bootcampproject.controllers;

import com.bootcampproject.dto.AdminCustomerResponseTO;
import com.bootcampproject.dto.AdminSellerResponseTO;
import com.bootcampproject.dto.CustomerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.entities.*;
import com.bootcampproject.exceptions.DataAlreadyPresentException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.services.AdminService;
import com.bootcampproject.services.CategoryService;
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
import java.util.List;

@RestController
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomer(@RequestHeader(name = "email", required = false) String email, @RequestHeader(name = "offset", required = false) Integer offset, @RequestHeader(name = "limit", required = false) Integer limit) {
        //instead of pagesize use limit
        try {
            if (email != null) {
                return new ResponseEntity<AdminCustomerResponseTO>(adminService.findCustomerByEmail(email), HttpStatus.OK);
            }
            List<AdminCustomerResponseTO> customers = adminService.findAllCustomers(PageRequest.of(offset, limit));
            return new ResponseEntity<List<AdminCustomerResponseTO>>(customers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception occured while finding customers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sellers")
    public ResponseEntity<?> getSeller(@RequestHeader(name = "email", required = false) String email, @RequestHeader(name = "offset", required = false) Integer offset, @RequestHeader(name = "limit", required = false) Integer limit) {
        try {
            if (email != null)
                return new ResponseEntity<AdminSellerResponseTO>(adminService.findSellerByEmail(email), HttpStatus.OK);
            return new ResponseEntity<Page<SellerResponseTO>>((Page<SellerResponseTO>) adminService.findAllSellers(PageRequest.of(offset, limit, Sort.by("id"))).getContent(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception occured while finding Sellers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/customer/activateordeactivate/{user_id}")
    public ResponseEntity<?> activateOrDeactivateCustomer(@PathVariable("user_id") Long user_id,@RequestParam(name = "active")Boolean active) {
        try {
            return new ResponseEntity<String>(adminService.activateOrDeactivateCustomer(user_id,active), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception occured while activating or deactivating a customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/user/lockorunlock/{user_id}")
    public ResponseEntity<?> lockOrUnlockUser(@PathVariable("user_id") Long user_id,@RequestParam(name = "lock")Boolean lock) {
        try {
            return new ResponseEntity<String>(adminService.lockOrUnlockUser(user_id,lock), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception occured while activating or deactivating a customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/seller/activateordeactivate/{user_id}")
    public ResponseEntity<?> activateOrDeactivateSeller(@PathVariable("user_id") Long user_id,Boolean active) {
        try {
        return new ResponseEntity<String>(adminService.activateOrDeactivateSeller(user_id,active), HttpStatus.OK);
    }catch(EntityNotFoundException e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch(Exception e)
    {
        return new ResponseEntity<String>("Exception occured while activating or deactivating a Seller", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @PostMapping("/addmetadata")
    public ResponseEntity<?> addMetadata(@RequestParam(name = "name")String name) {
        try {
            return new ResponseEntity<String>("Category Metadata saved and id is" + categoryService.addMetadata(name), HttpStatus.OK);
        }catch (DataAlreadyPresentException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            return new ResponseEntity<String>("Exception occured while saving Metadata Field",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/metadata")
    public ResponseEntity<?> getAllMetadata(@RequestHeader(name = "offset",required = false)Integer offset,@RequestHeader(name = "limit",required = false)Integer limit)
    {
        return new ResponseEntity<List<CategoryMetadataField>>(categoryService.getAllMetadata(PageRequest.of(offset,limit)),HttpStatus.OK);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestHeader(name = "categoryName")String categoryName,@RequestHeader(name = "parentId",required = false)Long id)
    {
        return new ResponseEntity<String>("Category saved with id is"+categoryService.addCategory(categoryName,id),HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id")Long id)
    {
        return new ResponseEntity<Category>(categoryService.getCategory(id),HttpStatus.OK);
    }
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(@RequestHeader(name = "offset",required = false)Integer offset,@RequestHeader(name = "limit",required = false)Integer limit)
    {
        return new ResponseEntity<List<Category>>(categoryService.getAllCategory(PageRequest.of(offset,limit,Sort.by("id"))),HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")Long id,@RequestBody String name)
    {
        return new ResponseEntity<Category>(categoryService.updateCategory(id,name),HttpStatus.OK);
    }

    @PostMapping("/metadataCategory")
    public ResponseEntity<?> createMetadata(@RequestBody CategoryMetadataFieldValues metadata)
    {
        return new ResponseEntity<CategoryMetadataFieldValues>(categoryService.addCategoryMetadata(metadata),HttpStatus.OK);
    }

}