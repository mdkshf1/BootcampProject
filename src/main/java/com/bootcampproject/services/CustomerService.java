package com.bootcampproject.services;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.repositories.AddressRepo;
import com.bootcampproject.repositories.CustomerRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private AddressRepo addressRepo;

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
        log.info("calling customer from customer TO");
        Customer customer = CustomerTO.createCustomer(customerTO);
        log.info("setting User of customer");
        customer.setUser(user);
        log.info("setting customer for user");
        user.setCustomer(customer);
        log.info("mail sending start");
        sendmail(customerTO.getEmail(), customer.getActivationToken());
        commonMail();
        System.out.println(customer);
        log.info("saving customer");
        customerRepo.save(customer);
        log.info("after saving customer returning to controller");
        return customerTO;
    }
    private void sendmail(String to,String uuid)
    {
        String subject = "This is to notify to activate your account";
        String text = "http://localhost:8080/activate/"+uuid;
        log.info(text);
        simpleMailService.sendMail(to,subject,text);
    }

    public void commonMail()
    {
        log.info("Sending a mail to ADMIN");
        String to="mohd.kashif@tothenew.com";
        String subject = "To Notify that a seller/customer have registered";
        String text = "You have to activate this user by changing tha is_active of a particular user";
        simpleMailService.sendMail(to,subject,text);
    }
    public Long findTime(Date d1,Date d2)
    {
        Long time = d2.getTime()-d1.getTime();
        long diffSeconds = time / 1000 % 60;
        long diffMinutes = time / (60 * 1000) % 60;
        return diffSeconds;
    }

    public void reactivateCustomer(Customer customer)
    {
        String token = UUID.randomUUID().toString();
        customer.setActivationToken(token);
        customer.setActivationTokenAt(new Date());
        customerRepo.save(customer);
        User user = userService.findUserById(customer.getId());
        sendmail(user.getEmail(),token);
    }

}
