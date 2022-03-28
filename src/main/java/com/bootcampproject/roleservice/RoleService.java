package com.bootcampproject.roleservice;


import com.bootcampproject.bootstrap.CreatingRole;
import com.bootcampproject.entities.Role;
import com.bootcampproject.repositories.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    Logger logger = LoggerFactory.getLogger(CreatingRole.class);

    public void creatingRoles() {

        logger.info("Creating ADMIN role");
        Role role1 = new Role();
        role1.setAuthority("admin");

        logger.info("Creating SELLER role");

        Role role2 = new Role();
        role2.setAuthority("seller");

        logger.info("Creating CUSTOMER role");
        Role role3 = new Role();
        role3.setAuthority("customer");

        roleRepo.save(role1);
        roleRepo.save(role2);
        roleRepo.save(role3);

    }

}
