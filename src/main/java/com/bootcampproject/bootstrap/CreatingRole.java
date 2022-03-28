package com.bootcampproject.bootstrap;

import com.bootcampproject.roleservice.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CreatingRole implements ApplicationRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Logger logger = LoggerFactory.getLogger(CreatingRole.class);

        logger.info("Creating roles");

        roleService.creatingRoles();

        logger.info("ALL Roles Created");
    }
}
