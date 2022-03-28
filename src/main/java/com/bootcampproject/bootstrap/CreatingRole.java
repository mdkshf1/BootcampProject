package com.bootcampproject.bootstrap;

import com.bootcampproject.finalfields.Finalfields;
import com.bootcampproject.roleservice.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import static com.bootcampproject.finalfields.Finalfields.*;

@Component
@Slf4j
public class CreatingRole implements ApplicationRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("Creating roles");
        String authority = roleService.check(admin);
        if (authority.equals(admin))
        {
            log.info("ADMIN role already created");
        }
        else
            roleService.save(admin);
        authority = roleService.check(seller);
        if (authority.equals(seller))
        {
            log.info("SELLER authority already created");
        }
        else
            roleService.save(seller);
        authority =roleService.check(customer);
        if(authority.equals(customer))
        {
            log.info("CUSTOMER auhtority already created");
        }
        else
            roleService.save(customer);
        log.info("roles created");
    }
}
