package com.bootcampproject.repositories;

import com.bootcampproject.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepo extends JpaRepository<Seller, Long> {

    Seller findByUserId(Long id);
}
