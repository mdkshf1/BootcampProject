/*
package com.bootcampproject.services;


import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SellerRepo sellerRepo;

    public SellerTO sellerDetails(SellerTO sellerTO)
    {
        String email = sellerTO.getEmail();
        User user = userRepo.findByEmail(email);
       SellerTO seller =  SellerTO.setDetails(sellerTO,user);
        Seller seller1 = Seller.setDetails(seller,user);
        sellerRepo.save(seller1);
        return seller;
    }
}
*/
