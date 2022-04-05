package com.bootcampproject.services;

import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UserTO;
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
    private ActivationMailService activationMailService;

    @Autowired
    private SimpleMailService simpleMailService;


/*    public UserTO createSeller(UserTO userTO)
    {
        User user1 = userRepo.findByEmail(userTO.getEmail());
        if(user1!=null) {
            return null;
        }
            Role role = roleRepo.findByAuthority(ROLE_SELLER);
            userTO.setRoles(Collections.singleton(role));
            User user = UserTO.mapper(userTO);
            try {
                activationMailService.sendMail(user);
                log.info("mail sent");
            } catch (MailException e) {
                log.info("Error in mail sending");
            }
            userRepo.save(user);
            return userTO;
    }*/

    public SellerTO createSeller(SellerTO sellerTO)
    {
        String email = sellerTO.getEmail();
        User nulluser = checkUser(email);
        if (nulluser!=null)
            return null;
        Role role = roleRepo.findByAuthority(ROLE_SELLER);
        sellerTO.setRoles(Collections.singleton(role));
        User user = UserTO.mapper(sellerTO);
        System.out.println(user);
        Seller seller = SellerTO.mapper(sellerTO,user);
        System.out.println(seller);
        try
        {
            activationMailService.sendMail(user);
            log.info("Mail Successfully sent");
        }catch (Exception e) {
            log.info("Error occurred while sending mail");
        }
        userRepo.save(user);
        sellerRepo.save(seller);
        return sellerTO;
    }

    public UserTO createCustomer(UserTO userTO)
    {
        User user1 = userRepo.findByEmail(userTO.getEmail());
        if(user1!=null)
        {
            return null;
        }
        Role role = roleRepo.findByAuthority(ROLE_CUSTOMER);
        userTO.setRoles(Collections.singleton(role));
        User user = UserTO.mapper(userTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return userTO;
    }

    public User checkUser(String email)
    {
        return userRepo.findByEmail(email);
    }

    public User checkUserByUuid(String uuid)
    {
        return userRepo.findByUuid(uuid);
    }

    public User activateUser(User user)
    {
/*        user.setLocked(false);*/
        user.setActive(false);
        userRepo.save(user);
        System.out.println(user);
        return user;
    }

    public void mailUser(User user)
    {
        try
        {
            simpleMailService.sendMail(user);
            log.info("Activation mail sent");
        }
        catch (Exception e)
        {
            log.info("Exception in sending mail");
        }
    }
}