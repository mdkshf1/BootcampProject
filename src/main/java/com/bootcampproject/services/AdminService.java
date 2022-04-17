package com.bootcampproject.services;

import com.bootcampproject.dto.AdminCustomerResponseTO;
import com.bootcampproject.dto.AdminSellerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.EntityNotFoundException;
import com.bootcampproject.repositories.CustomerRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bootcampproject.constants.AppConstant.ROLE_ADMIN;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    //autowire user service
    @Autowired
    private UserService userService;

    @Autowired
    private SimpleMailService simpleMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public User createAdmin(UserTO userTO)
    {
/*        User user = userRepo.findByEmail(userTO.getEmail());
        if (user != null)
        {
            throw new UserAlreadyExistException("Admin with this mail already exist");
        }*/
        Role role = roleRepo.findByAuthority(ROLE_ADMIN);
        userTO.setRoles(Collections.singleton(role));
        log.info("ROLE SET FOR ADMIN");
        User user;
        user = User.create(userTO);
        user.setPassword(passwordEncoder.encode(userTO.getPassword()));
        log.info("password encoded");
        user.setActive(true);
        log.info("activating and setting role");
        userRepo.save(user);
        return user;
    }


    //rename findByEmail and move to customerService
    public AdminCustomerResponseTO findCustomerByEmail(String email)
    {
        User user = userService.findByEmail(email);
        Customer customer = user.getCustomer();
        if (customer==null)
            throw new EntityNotFoundException("Customer with "+email+" is not found");
        return AdminCustomerResponseTO.getCustomer(user);
    }
    public List<AdminCustomerResponseTO> findAllCustomers(Pageable pageable)
    {
        List<AdminCustomerResponseTO> customers = new ArrayList<>();
        Page<Customer> customerPage= customerRepo.findAll(pageable);
        List<Customer> customerList = customerPage.getContent();
        if (customerList.isEmpty())
            throw new EntityNotFoundException("There is no user present");
        customerList.stream().forEach(customer -> {
            User user = customer.getUser();
            AdminCustomerResponseTO adminCustomerResponseTO = AdminCustomerResponseTO.getCustomer(user);
            customers.add(adminCustomerResponseTO);
        });
/*        if (customerPage.isEmpty())
            throw new EntityNotFoundException("There is no user present");
        customerPage.stream().forEach(customer -> {
            User user = customer.getUser();
            AdminCustomerResponseTO adminCustomerResponseTO = AdminCustomerResponseTO.getCustomer(user);
            customers.add(adminCustomerResponseTO);
        });*/
       /* for (Customer customer:customerList
             ) {
            User user = customer.getUser();
            AdminCustomerResponseTO adminCustomerResponseTO = AdminCustomerResponseTO.getCustomer(user);
            customers.add(adminCustomerResponseTO);
        }*/
        return customers;
    }

    public AdminSellerResponseTO findSellerByEmail(String email)
    {
        User user = userService.findByEmail(email);
        Seller seller = user.getSeller();
        if (seller == null)
            throw new EntityNotFoundException("Seller with "+email+" is not found");
        return AdminSellerResponseTO.mapper(user);
    }
    public Page<SellerResponseTO> findAllSellers(Pageable pageable)
    {
        List<SellerResponseTO> sellerResponseTOList = new ArrayList<>();
        List<Seller> sellerList = sellerRepo.findAll();
        if (sellerList.isEmpty())
            throw new EntityNotFoundException("There is no user found");
        for (Seller seller:sellerList
             ) {
            User user = seller.getUser();
            SellerResponseTO sellerResponseTO = SellerResponseTO.getSeller(seller,user);
            sellerResponseTOList.add(sellerResponseTO);
        }
        return (Page<SellerResponseTO>) sellerResponseTOList;
    }

/*    public String activateCustomer(Long user_id)
    {
        Customer customer = customerRepo.findById(user_id).get();
        if (customer == null)
            throw new UserNotFoundException("Unable to find customer with this user_id");
        User user = userService.findUserById(user_id);
        if (user.isActive())
            return "Customer already activated";
        userService.activateUser(user);
        return "Customer has been activated";
    }

    public String deactivateCustomer(Long user_id)
    {
        Customer customer = customerRepo.findById(user_id).get();
        if (customer ==null)
            throw new UserNotFoundException("Unable to find Customer with this user_id");
        User user = userService.findUserById(user_id);
        if (!user.isActive())
            return "Customer already Deactivated";
        userService.deactivateUser(user);
        return "Customer has been DeActivated";
    }*/
/*    public String activateSeller(Long user_id)
    {
        Seller seller = sellerRepo.findById(user_id).get();
        if (seller == null)
            throw new UserNotFoundException("Unable to find seller with this user_id");
        User user = userService.findUserById(user_id);
        if (user.isActive())
            return "Seller already activated";
        userService.activateUser(user);
        return "Seller has been activated";
    }
    public String deactivateSeller(Long user_id)
    {
        Seller seller = sellerRepo.findById(user_id).get();
        if (seller == null)
            throw new UserNotFoundException("Unable to find seller with this user_id");
        User user = userService.findUserById(user_id);
        if (!user.isActive())
            return "Seller already deactivated";
        userService.deactivateUser(user);
        return "Seller has been deactivated";
    }*/

    public String activateOrDeactivateSeller(Long user_id)
    {
        Seller seller = sellerRepo.findByUserId(user_id);
        if (seller == null)
            throw new EntityNotFoundException("Customer with this id cannot be found");
        User user = seller.getUser();
        boolean b = user.isActive();
        if (b==true)
        {
            user.setActive(false);
            seller.setUser(user);
            sellerRepo.save(seller);
            simpleMail(user.getEmail(),"Info about account","This is to inform that your account got deactivated by admin\nfor help contact admin");
            return "Customer was active and now deactivated";
        }
        else{
            user.setActive(true);
            seller.setUser(user);
            sellerRepo.save(seller);
            simpleMail(user.getEmail(),"Info about account","This is to inform that your account got activated by admin\nNow enjoy being with us");
            return "Customer was deactivated and now activated";
        }
    }

    public String activateOrDeactivateCustomer(Long user_id)
    {
        Customer customer = customerRepo.findByUserId(user_id);
        if (customer == null)
            throw new EntityNotFoundException("Customer with this id cannot be found");
        User user = customer.getUser();
        boolean b = user.isActive();
        if (b==true)
        {
            user.setActive(false);
            customer.setUser(user);
            customerRepo.save(customer);
            simpleMail(user.getEmail(),"Info about account","This is to inform that your account got deactivated by admin\nfor help contact admin");
            return "Customer was active and now deactivated";
        }
        else{
            user.setActive(true);
            customer.setUser(user);
            customerRepo.save(customer);
            simpleMail(user.getEmail(),"Info about account","This is to inform that your account got activated by admin\nEnjoy being with us");
            return "Customer was deactivated and now activated";
        }
    }
    private void simpleMail(String to,String subject,String text)
    {
        simpleMailService.sendMail(to,subject,text);
    }
}
