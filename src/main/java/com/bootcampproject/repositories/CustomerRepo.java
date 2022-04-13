package com.bootcampproject.repositories;

import com.bootcampproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByActivationToken(String activationToken);

    List<Customer> findAll();
}
