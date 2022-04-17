package com.bootcampproject.services;

import com.bootcampproject.dto.CustomerTO;
import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Customer;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.NoEntityFoundException;
import com.bootcampproject.exceptions.UserNotActivatedException;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
    private TokenStore tokenStore;

    @Autowired
    private SimpleMailService simpleMailService;



    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public User findUserById(Long id) {
        return userRepo.findById(id).get();
    }

    public User activateUser(User user) {
        user.setActive(true);
        userRepo.save(user);
        String subject = "This is to notify that your account has been activated";
        String text = "As your account has been activated now you can enjoy our service as per criteria";
        simpleMailService.sendMail(user.getEmail(),subject,text);
        return user;
    }

    public User deactivateUser(User user) {
        user.setActive(false);
        userRepo.save(user);
        String subject = "This is to notify that your account has been deactivated";
        String text = "As your account has been deactivated now you cannot enjoy our service as per criteria";
        simpleMailService.sendMail(user.getEmail(),subject,text);
        return user;
    }

    public void forgotPassword(User user)
    {
        if (!user.isActive())
            throw new UserNotActivatedException("This user is not activated\nFirst activate your account by admin and then you can change your password");
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
        user.setPassword(passwordEncoder.encode(password));
        String subject = "Your Password has been updated";
        String body = "As your password has been updated now you can login with your new password";
        simpleMailService.sendMail(user.getEmail(),subject,body);
        return userRepo.save(user);
    }

    public String logOut(String token)
    {
        if (token == null)
            throw new NoEntityFoundException("No token found\nOr you are already logged out");
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        String finalToken = token.replace("Bearer ","").trim();
        tokenStore.removeAccessToken(tokenStore.readAccessToken(finalToken));
        return "Logged Out Successfully";
    }

}