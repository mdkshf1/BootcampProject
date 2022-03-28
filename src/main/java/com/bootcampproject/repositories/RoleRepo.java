package com.bootcampproject.repositories;

import com.bootcampproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role,Long> {

    @Query(value = "SELECT DISTINCT authority FROM role WHERE authority=:authority",nativeQuery = true)
    public String chechexist(@Param("authority") String auhtority);
}
