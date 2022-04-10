package com.bootcampproject.repositories;

import com.bootcampproject.dto.CustomerResponseTO;
import com.bootcampproject.entities.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByActivationToken(String activationToken);

    List<Customer> findAll();
}
