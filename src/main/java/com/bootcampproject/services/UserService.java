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




    //rename findbyEmail
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
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

    public void forgotPassword(User user)
    {
        user.setForgotPasswordToken(UUID.randomUUID().toString());
        log.info("new token "+user.getForgotPasswordToken());
        user.setForgotPasswordAt(new Date());
        String subject = "Please click on the link to change your password";
        String text = "http://localhost:8080/changepassword/"+user.getForgotPasswordToken();
        log.info("Forgot password link "+text);
        simpleMailService.sendMail(user.getEmail(),subject,text);
        userRepo.save(user);
    }

    public User findUserByToken(String token)
    {
        return userRepo.findByForgotPasswordToken(token);
    }
    public User changePassword(User user,String password)
    {
        user.setPassword(password);
        return userRepo.save(user);
    }
}