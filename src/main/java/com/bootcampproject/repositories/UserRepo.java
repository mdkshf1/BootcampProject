package com.bootcampproject.repositories;


import com.bootcampproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByForgotPasswordToken(String token);

    @Override
    Optional<User> findById(Long aLong);

    //findAll in customer Repo by using left join..
    //do it by using JOIN
    //findAll in customer
/*@Query(value = "Select user.id,user.first_name,user.middle_name,user.last_name,user.is_active from user INNER JOIN customer on user.id = customer.user_id",nativeQuery = true)
*//*@Query("SELECT c.user_id,u.firstName,u.middleName,u.lastName,u.isActive FROM user u INNER JOIN customer c ON c.user_id=u.id")*//*
Page<CustomerResponseTO> findAllCustomers();*/



}
