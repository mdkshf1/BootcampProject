package com.bootcampproject.roleservice;

import com.bootcampproject.entities.Role;
import com.bootcampproject.repositories.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public void save(String authority) {

        Role role = new Role();
        role.setAuthority(authority);
        roleRepo.save(role);
    }

    public Long check(String authority) {
        return roleRepo.countByAuthority(authority);
    }
}