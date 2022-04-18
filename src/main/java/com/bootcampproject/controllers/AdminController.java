package com.bootcampproject.controllers;

import com.bootcampproject.dto.*;
import com.bootcampproject.entities.*;
import com.bootcampproject.exceptions.DataAlreadyPresentException;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.services.AdminService;
import com.bootcampproject.services.CategoryService;
import com.bootcampproject.services.ProductService;
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

    @Autowired
    private ProductService productService;

//<<<<<<<<<<<<<<<<<<<<<<<<<<------------------------Register Customer API------------------------>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomer(@RequestParam(name = "email", required = false) String email, @RequestParam(name = "offset", required = false,defaultValue = "0") Integer offset, @RequestParam(name = "limit", required = false,defaultValue = "2") Integer limit) {
        try {
            if (email != null) {
                return new ResponseEntity<AdminCustomerResponseTO>(adminService.findCustomerByEmail(email), HttpStatus.OK);
            }
            List<AdminCustomerResponseTO> customers = adminService.findAllCustomers(PageRequest.of(offset,limit));
            return new ResponseEntity<List<AdminCustomerResponseTO>>(customers, HttpStatus.OK);
        }catch (Exception e) {

            return new ResponseEntity<String>(/*Exception.customers*/"Exception occured while finding customers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<-------------------------Register Seller API------------------------------------------>>>>>>>>>>>
    @GetMapping("/sellers")
    public ResponseEntity<?> getSeller(@RequestParam(name = "email", required = false) String email, @RequestParam(name = "offset", required = false,defaultValue = "0") Integer offset, @RequestParam(name = "limit", required = false,defaultValue = "2") Integer limit) {
        try {
            // Global exception handling(Best Practice)
            //beware of exception Swallowing
            if (email != null)
                return new ResponseEntity<AdminSellerResponseTO>(adminService.findSellerByEmail(email), HttpStatus.OK);
            List<AdminSellerResponseTO> sellers = adminService.findAllSellers(PageRequest.of(offset,limit));
            return new ResponseEntity<List<AdminSellerResponseTO>>(sellers, HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error in finding "+e.getMessage());
            return new ResponseEntity<String>(/*Exception.sellers*/"Exception occured while finding Sellers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//<<<<<<<<<<<<<<<<<<<<---------------------Activate Or Deactivate Customer----------------->>>>>>>>>>>>>>>>
    @PatchMapping("/customer/activateordeactivate/{user_id}")
    public ResponseEntity<?> activateOrDeactivateCustomer(@PathVariable("user_id") Long user_id,@RequestParam(name = "active")Boolean active) {
        try {
            return new ResponseEntity<String>(adminService.activateOrDeactivateCustomer(user_id,active), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(/*Exception.activate.deactivate.customer*/"Exception occured while activating or deactivating a customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//<<<<<<<<<<<<<<<<<<<<<<<<------------------------Lock or Unlock User---------------------->>>>>>>>>>>>>>>>>>>>>
    @PatchMapping("/user/lockorunlock/{user_id}")
    public ResponseEntity<?> lockOrUnlockUser(@PathVariable("user_id") Long user_id,@RequestParam(name = "lock")Boolean lock) {
        try {
            //put vs patch difference
            //dheerajkumarmadaan
            return new ResponseEntity<String>(adminService.lockOrUnlockUser(user_id,lock), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(/*Exception.lock.unlock.user*/"Exception occured while locking or unlocking a customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<<<<<<-----------------------Activate or Deactivate Seller---------------------->>>>>>>>>>>>>>>>
    @PatchMapping("/seller/activateordeactivate/{user_id}")
    public ResponseEntity<?> activateOrDeactivateSeller(@PathVariable("user_id") Long user_id,Boolean active) {
        try {
        return new ResponseEntity<String>(adminService.activateOrDeactivateSeller(user_id,active), HttpStatus.OK);
    }catch(EntityNotFoundException e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch(Exception e)
    {
        return new ResponseEntity<String>(/*Exception.activate.deactivate.seller*/"Exception occured while activating or deactivating a Seller", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//<<<<<<<<<<<<<<<<<<------------------------Category API------------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



//<<<<<<<<<<<<<<<<<<<<--------------=-----------Add Metadata API-------=------------------------------->>>>>>>>>>>>>>>>>>>>


    @PostMapping("/addmetadata")
    public ResponseEntity<?> addMetadata(@RequestParam(name = "name")String name) {
        try {
            return new ResponseEntity<String>("Category Metadata saved and id is " + categoryService.addMetadata(name), HttpStatus.OK);
        }catch (DataAlreadyPresentException e)
        {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            log.error("Exception occured while saving Metadata Field",e);
            return new ResponseEntity<String>(/*Exception.metadata.field*/"Exception occured while saving Metadata Field",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//<<<<<<<<<<<<<<<<<<<-------------------------Get Metadata API with pagination---------------------------------------->>>>>>>>>>>>>>>>>>>
    @GetMapping("/metadata")
    public ResponseEntity<?> getAllMetadata(@RequestParam(name = "offset",required = false,defaultValue = "0")Integer offset,@RequestParam(name = "limit",required = false,defaultValue = "3")Integer limit)
    {
        List<CategoryMetadataField> metadataFields = categoryService.getAllMetadata(PageRequest.of(offset,limit));
        return new ResponseEntity<List<CategoryMetadataField>>(metadataFields,HttpStatus.OK);
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<<--------------------------Add Category to Category Table API--------------------------->>>>>>>>>>>>>>>>>>>
    @PostMapping("/addcategory")
    public ResponseEntity<?> addCategory(@RequestParam(name = "categoryName")String categoryName,@RequestParam(name = "parentId",required = false)Long id)
    {
        return new ResponseEntity<String>("Category saved with id is "+categoryService.addCategory(categoryName,id),HttpStatus.OK);
    }
//<<<<<<<<<<<<<<<<<<---------------------------------Get a Category with Category Id------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id")Long id)
    {
        try {
            return new ResponseEntity<CategoryResponseTO>(categoryService.getCategory(id), HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            log.error("Id not found "+e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            log.error("Exception occurred "+e);
            return new ResponseEntity<String>("Exception occured while getting category with this id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<------------------------------Get All the Categories----------------------->>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(@RequestParam(name = "offset",required = false,defaultValue = "0")Integer offset,@RequestParam(name = "limit",required = false,defaultValue = "3")Integer limit)
    {
        List<CategoryResponseTO> categories = categoryService.getAllCategory(PageRequest.of(offset,limit));
        return new ResponseEntity<List<CategoryResponseTO>>(categories,HttpStatus.OK);
    }
//<<<<<<<<<<<<<<<<<<<<<-----------------------Update a Category------------------------>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @PutMapping("/updatecategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id")Long id,@RequestParam(name = "categoryName") String name)
    {
        return new ResponseEntity<Category>(categoryService.updateCategory(id,name),HttpStatus.OK);
    }

//<<<<<<<<<<<<<<<<<<<<<<<<<<<<---------------------------------------Add Metadata for a category------------------>>>>>>>>>>>>>>>>>>>
    @PostMapping("/addmetadatacategory")
    public ResponseEntity<?> addmetadataCategory(@RequestParam(name = "categoryId")Long categoryId,@RequestParam(name = "fieldId")Long fieldId,@RequestParam(name = "Values")String values)
    {
        try {
            categoryService.addMetadataCategory(categoryId,fieldId,values);
            return new ResponseEntity<String>("Values Added and metadata created",HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            log.error("Entity not found "+e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            log.error("Exception occured "+e);
            return new ResponseEntity<String>("Exception occured while adding metadata",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//<<<<<<<<<<<<<<<<<----------------------Update medata Category----------------->>>>>>>>>>>>>>>>>>>>>>>>>.
@PutMapping("/update/metadatacategory")
public ResponseEntity<?> updateMetadataCategory(@RequestParam(name = "categoryId")Long categoryId,@RequestParam(name = "fieldId")Long fieldId,@RequestParam(name = "Values")String values)
{
    try {
        categoryService.updateMetadataValue(values,categoryId,fieldId);
        return new ResponseEntity<String>("Metadata Field Values Updated",HttpStatus.OK);
    }catch (EntityNotFoundException e)
    {
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }catch (Exception e)
    {
        return new ResponseEntity<>("Exception occurred while updating category metadata",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<---------------------View Product from ProductId---------------------->>>>>>>>>>>>>>>>
    @GetMapping("/viewproduct/{product_id}")
    public ResponseEntity<?> viewProductforAdmin(@PathVariable(name = "product_id")Long product_id) {
        try {
            return new ResponseEntity<CustomerProductResponseTO>(productService.viewProductCustomer(product_id), HttpStatus.OK);
        }catch (EntityNotFoundException e)
        {
            log.error("User with this userId not found "+e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
//<<<<<<<<<<<<<<<<<<<-------------------------View All the products ---------------------->>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/viewproducts")
    public ResponseEntity<?> viewAllProductForAdmin(@RequestParam(name = "offset", required = false,defaultValue = "0") Integer offset, @RequestParam(name = "limit", required = false,defaultValue = "2") Integer limit)
    {
        List<CustomerProductResponseTO> products = productService.viewAllProductForCustomer(PageRequest.of(offset,limit));
        return new ResponseEntity<List<CustomerProductResponseTO>>(products,HttpStatus.OK);
    }

//<<<<<<<<<<<<<<<<<<<<<<<<----------------------Activate or deactivate product by product Id---------------->>>>>>>>>>>>>>>>>>>>>>>
    @PutMapping("/product/activateordeactivate/{product_id}")
    public ResponseEntity<?> activateOrDeactivateProduct(@RequestParam(name = "status")Boolean status,@PathVariable(name = "product_id")Long product_id)
    {
        try {
            return new ResponseEntity<String>(productService.activateOrDeactivateProduct(product_id,status), HttpStatus.OK);
        }catch(EntityNotFoundException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(Exception e)
        {
            return new ResponseEntity<String>("Exception occured while activating or deactivating a Seller", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}