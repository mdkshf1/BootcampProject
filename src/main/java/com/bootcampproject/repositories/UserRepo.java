package com.bootcampproject.repositories;


import com.bootcampproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

}
