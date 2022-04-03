package com.bootcampproject.services;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;
import static com.bootcampproject.constants.AppConstant.ROLE_SELLER;


@Service
@Slf4j
public class UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;


    public UserTO createSeller(UserTO userTO)
    {
        Role role = roleRepo.findByAuthority(ROLE_SELLER);
        userTO.setRoles(Collections.singleton(role));
        User user = UserTO.mapper(userTO);
        userRepo.save(user);
        return userTO;
    }

    public UserTO createCustomer(UserTO userTO)
    {
        Role role = roleRepo.findByAuthority(ROLE_CUSTOMER);
        userTO.setRoles(Collections.singleton(role));
        User user = UserTO.mapper(userTO);
        userRepo.save(user);
        return userTO;
    }
}