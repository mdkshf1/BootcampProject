package com.bootcampproject.bootstrap;

import com.bootcampproject.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import static com.bootcampproject.constants.AppConstant.*;

@Component
@Slf4j
public class DataInitialiser implements ApplicationRunner {

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleService.check(ROLE_ADMIN) == 0)
            roleService.save(ROLE_ADMIN);
        if (roleService.check(ROLE_SELLER) == 0)
            roleService.save(ROLE_SELLER);
        if (roleService.check(ROLE_CUSTOMER) == 0)
            roleService.save(ROLE_CUSTOMER);
    }
}
