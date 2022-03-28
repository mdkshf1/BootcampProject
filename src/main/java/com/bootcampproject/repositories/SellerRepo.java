package com.bootcampproject.repositories;

import com.bootcampproject.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller,Long> {
}
