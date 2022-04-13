package com.bootcampproject.services;


import com.bootcampproject.dto.CustomerResponseTO;
import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.repositories.AddressRepo;
import com.bootcampproject.repositories.CustomerRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(customerTO.getPassword()));
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

    public CustomerResponseTO getDetails()
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        return CustomerResponseTO.mapper(user);
    }

    public List<Address> getAddressDetails()
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        List<Address> address = user.getAddress();
        return address;
    }
    public void updateDetails(CustomerTO customerTO)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        if (user.getEmail() != customerTO.getEmail())
            throw new CannotChangeException("You cannot change email");
        if (customerTO.getPassword() != null)
            throw new CannotChangeException("You cannot change Password\nTo change please hit /changePassword API");
        if (customerTO.getFirstName() != null)
            user.setFirstName(customerTO.getFirstName());
        if (customerTO.getMiddleName() != null)
            user.setMiddleName(customerTO.getMiddleName());
        if (customerTO.getLastName() !=null)
            user.setLastName(customerTO.getLastName());
        Customer customer = user.getCustomer();
        customer.setContact(customerTO.getContact());
        userRepo.save(user);
        customerRepo.save(customer);
    }

    public void addAddress(Address address)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        List<Address> addressList = user.getAddress();
        addressList.add(address);
        user.setAddress(addressList);
        userRepo.save(user);
    }
    public Integer deleteAddress(Long id)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        List<Address> addressList = user.getAddress();
        for (Address address:addressList) {
            if (address.getId() != id)
                return 0;
        }
        addressList.removeIf(address -> address.getId()==id);
        user.setAddress(addressList);
        userRepo.save(user);
        return 1;
    }


    //change in address update
    public Integer updateAddress(Long id,Address address)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        List<Address> addressList = user.getAddress();
        for (Address address1:addressList) {
            if (address1.getId() == id) {
                if (address.getCity() != null)
                    address1.setCity(address.getCity());
                if (address.getState() != null)
                    address1.setState(address.getState());
                if (address.getCountry() != null)
                    address1.setCountry(address.getCountry());
                if (address.getAddressLine() != null)
                    address1.setAddressLine(address.getAddressLine());
                if (address.getZipCode() != null)
                    address1.setZipCode(address.getZipCode());
                user.setAddress(addressList);
                userRepo.save(user);
                return 1;
            }
        }
        return 0;
    }
}
