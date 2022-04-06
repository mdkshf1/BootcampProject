package com.bootcampproject.repositories;


import com.bootcampproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
   @Query(value = "Select email from user where uuid =:uuid", nativeQuery = true)
    String findMail(@Param("uuid") String uuid);
}
