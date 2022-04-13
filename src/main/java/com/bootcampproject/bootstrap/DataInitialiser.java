package com.bootcampproject.bootstrap;

import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleService.check(ROLE_ADMIN) == 0)
            roleService.save(ROLE_ADMIN);
        if (roleService.check(ROLE_SELLER) == 0)
            roleService.save(ROLE_SELLER);
        if (roleService.check(ROLE_CUSTOMER) == 0)
            roleService.save(ROLE_CUSTOMER);

        List<User> userList = userRepo.findAll();

        System.out.println("List found for users is "+ userList);


        // Admin role is not setting

/*        if (userList.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setFirstName("Mohd");
            user.setMiddleName("Kashif");
            user.setLastName("Multani");
            user.setPassword(passwordEncoder.encode("Admin@12345"));
            user.setDeleted(false);
            user.setActive(true);
            user.setExpired(false);
            user.setLocked(false);
*//*            Role role = roleRepo.findByAuthority(ROLE_ADMIN);
            user.setRoles(Collections.singleton(role));*//*
            Role role = roleRepo.findByAuthority(ROLE_ADMIN);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userRepo.save(user);
        }*/
    }
}
