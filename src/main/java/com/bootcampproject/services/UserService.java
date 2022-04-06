package com.bootcampproject.services;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;
import static com.bootcampproject.constants.AppConstant.ROLE_SELLER;


@Service
@Slf4j
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private SimpleMailService simpleMailService;




    public User checkUser(String email) {
        return userRepo.findByEmail(email);
    }

    public String checkUserByUuid(String uuid) {
        return userRepo.findMail(uuid);
    }

    public User findUserById(Long id) {
        return userRepo.getById(id);
    }

    public User activateUser(User user) {
        user.setActive(true);
        userRepo.save(user);
        System.out.println(user);
        return user;
    }

}