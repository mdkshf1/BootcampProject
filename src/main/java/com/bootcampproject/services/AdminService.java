package com.bootcampproject.services;

import com.bootcampproject.dto.CustomerResponseTO;
import com.bootcampproject.dto.SellerResponseTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserNotFoundException;
import com.bootcampproject.repositories.CustomerRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    //autowire user service

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private CustomerRepo customerRepo;

    //rename findByEmail and move to customerService
    public CustomerResponseTO findCustomerByEmail(String email)
    {
        User user = userRepo.findByEmail(email);
        if (user==null)
            throw new UserNotFoundException("Customer with "+email+" is not found");
        return CustomerResponseTO.getCustomer(user);
    }
/*    public List<CustomerResponseTO> getAllCustomer()
    {
        //limit,offset and sort get from request param no need of sorting
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"id"));
        Page<CustomerResponseTO> users = userRepo.findAllCustomers(pageable);
        List<CustomerResponseTO> customers = users.getContent();

       List<Customer> customerList = customerRepo.findAll();

        return customers;
    }*/
/*    //move to seller service
    public SellerResponseTO getSingleSeller(String email)
    {
        User user = userRepo.findByEmail(email);
        if (user==null)
            throw new UserNotFoundException("Seller with "+email+" is not found");
        Seller seller = sellerRepo.findById(user.getId());
        return SellerResponseTO.getSeller(seller,user);
    }
    public List<SellerResponseTO> getAllSeller()
    {
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,id));
        Page<SellerResponseTO> users =
    }*/
}
