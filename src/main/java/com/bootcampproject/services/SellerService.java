
package com.bootcampproject.services;


import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.repositories.AddressRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Collections;

import static com.bootcampproject.constants.AppConstant.ROLE_CUSTOMER;
import static com.bootcampproject.constants.AppConstant.ROLE_SELLER;

@Service
@Slf4j
public class SellerService {

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;



    public SellerTO createSeller(SellerTO sellerTO)
    {
        User user = userRepo.findByEmail(sellerTO.getEmail());
        if (user!=null)
        {
            throw new UserAlreadyExistException("User already exist with this mail"+sellerTO.getEmail());
        }
        Role role = roleRepo.findByAuthority(ROLE_SELLER);
        sellerTO.setRoles(Collections.singleton(role));
        log.info("ROLE SET");
        user = User.create(sellerTO);
        System.out.println(user);
        Address address = sellerTO.getAddress();
/*        Seller seller = Seller.setDetails(sellerTO);*/
        Seller seller =SellerTO.mapper(sellerTO,user);
        address.setUser(user);
        seller.setUser(user);
        user.setSeller(seller);
        System.out.println(seller);
        sellerRepo.save(seller);
        addressRepo.save(address);
        return sellerTO;

    }
}
