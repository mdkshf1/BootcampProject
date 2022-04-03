package com.bootcampproject.repositories;

import com.bootcampproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Long countByAuthority(String authority);
    Role findByAuthority(String authority);
}
