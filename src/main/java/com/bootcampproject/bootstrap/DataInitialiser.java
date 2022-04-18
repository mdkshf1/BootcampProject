package com.bootcampproject.bootstrap;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.services.AdminService;
import com.bootcampproject.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.bootcampproject.constants.AppConstant.*;

@Component
@Slf4j
public class DataInitialiser implements ApplicationRunner {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleService.check(ROLE_ADMIN) == 0)
            roleService.save(ROLE_ADMIN);
        if (roleService.check(ROLE_SELLER) == 0)
            roleService.save(ROLE_SELLER);
        if (roleService.check(ROLE_CUSTOMER) == 0)
            roleService.save(ROLE_CUSTOMER);
        List<User> userList = userRepo.findAll();
          if (userList.isEmpty()) {
            UserTO user = new UserTO();
            user.setEmail("admin@gmail.com");
            user.setFirstName("Mohd");
            user.setMiddleName("Kashif");
            user.setLastName("Multani");
            user.setPassword("Admin@12345");
            user.setConfirmPassword("Admin@12345");
            adminService.createAdmin(user);
            log.info("exit from admin service");
        }
    }
}
