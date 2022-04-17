package com.bootcampproject.services;

import com.bootcampproject.dto.AddressUpdateTO;
import com.bootcampproject.dto.AdminSellerResponseTO;
import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.dto.SellerUpdateTO;
import com.bootcampproject.entities.Address;
import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.Seller;
import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.CannotChangeException;
import com.bootcampproject.exceptions.UserAlreadyExistException;
import com.bootcampproject.repositories.AddressRepo;
import com.bootcampproject.repositories.RoleRepo;
import com.bootcampproject.repositories.SellerRepo;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.utils.SecurityContextHolderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.bootcampproject.constants.AppConstant.ROLE_SELLER;

@Service
@Slf4j
public class SellerService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SellerRepo sellerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private CustomerService customerService;



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
        user.setPassword(passwordEncoder.encode(sellerTO.getPassword()));
        System.out.println(user);
        Address address = sellerTO.getAddress();
        Seller seller =SellerTO.mapper(sellerTO,user);
        address.setUser(user);
        seller.setUser(user);
        user.setSeller(seller);
        System.out.println(seller);
        System.out.println(address);
        customerService.commonMail();
        sellerRepo.save(seller);
        addressRepo.save(address);
        return sellerTO;
    }

    public AdminSellerResponseTO getDetails()
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        Seller seller = user.getSeller();
        return AdminSellerResponseTO.mapper(user);
    }

    public void updateDetails(SellerUpdateTO sellerTO)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        Seller seller = user.getSeller();
        if (sellerTO.getEmail() != null)
            throw new CannotChangeException("You cannot change email");
        if (sellerTO.getPassword() != null)
            throw new CannotChangeException("You cannot change Password\nTo change please hit /changePassword API");
        if (sellerTO.getFirstName() != null)
            user.setFirstName(sellerTO.getFirstName());
        if (sellerTO.getMiddleName() != null)
            user.setMiddleName(sellerTO.getMiddleName());
        if (sellerTO.getLastName() !=null)
            user.setLastName(sellerTO.getLastName());
        if (sellerTO.getCompanyContact() != null)
            seller.setCompanyContact(sellerTO.getCompanyContact());
        if (seller.getCompanyName() != null)
            seller.setCompanyName(sellerTO.getCompanyName());
        if (sellerTO.getGst() != null)
            seller.setGst(sellerTO.getGst());
        userRepo.save(user);
        sellerRepo.save(seller);
    }

    public void updatePassword(String password)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        userService.changePassword(user,password);
    }

    public void updateAddress(AddressUpdateTO address1)
    {
        String email = SecurityContextHolderUtil.getCurrentUserEmail();
        User user = userService.findByEmail(email);
        Address address = user.getAddress().get(0);
        if (address1.getCity() != null)
            address.setCity(address1.getCity());
        if (address1.getState() != null)
            address.setState(address1.getState());
        if (address1.getCountry() != null)
            address.setCountry(address1.getCountry());
        if (address1.getAddressLine() != null)
            address.setAddressLine(address1.getAddressLine());
        if (address1.getLabel() != null)
            address.setLabel(address1.getLabel());
        if (address1.getZipCode() != null)
            address.setZipCode(address1.getZipCode());
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        user.setAddress(addressList);
        userRepo.save(user);
    }
}
