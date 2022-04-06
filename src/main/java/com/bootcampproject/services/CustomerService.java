package com.bootcampproject.services;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.repositories.CustomerRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private SimpleMailService simpleMailService;




    public Customer findCustomerByToken(String token)
    {
        return customerRepo.findByActivationToken(token);
    }

    public CustomerTO createCustomer(CustomerTO customerTO)
    {
        User user = userRepo.findByEmail(customerTO.getEmail());
        if(user != null) {
            throw new UserAlreadyExistException("User Already exist email:" + customerTO.getEmail());
        }
        Role role = roleRepo.findByAuthority(ROLE_CUSTOMER);
        customerTO.setRoles(Collections.singleton(role));
        log.info("ROLE SET");
        user = User.create(customerTO);
        System.out.println(user);
        Customer customer = Customer.createCustomer(customerTO);
        customer.setUser(user);
        user.setCustomer(customer);
        log.info("mail sending start");
        sendmail(customerTO.getEmail(), customer.getActivationToken());
        System.out.println(customer);
        customerRepo.save(customer);
        return customerTO;
    }
    private void sendmail(String to,String uuid)
    {
        String subject = "This is to notify to activate your account";
        String text = "http://localhost:8080/activate/"+uuid;
        log.info(text);
        simpleMailService.sendMail(to,subject,text);
    }
}
