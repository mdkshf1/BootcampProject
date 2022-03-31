package com.bootcampproject.services;

import com.bootcampproject.dto.UserTO;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bootcampproject.constants.AppConstant.*;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    public UserTO create(UserTO userTO)
    {
        User user = new User();
        UserTO user1 = new UserTO();
        user.setEmail(userTO.getEmail());
        user.setFirstName(userTO.getFirstName());
        user.setMiddleName(userTO.getMiddleName());
        user.setLastName(userTO.getLastName());
        user.setPassword(userTO.getPassword());
        user.setRole(roleRepo.findByAuthority(ROLE_ADMIN));
        user.isActive();
        user1.setEmail(userTO.getEmail());
        user1.setFirstName(userTO.getFirstName());
        user1.setMiddleName(userTO.getMiddleName());
        user1.setLastName(userTO.getLastName());
        user1.setPassword(userTO.getPassword());
        user1.setRole(roleRepo.findByAuthority(ROLE_ADMIN));
        userRepo.save(user);
        return user1;
    }
}
