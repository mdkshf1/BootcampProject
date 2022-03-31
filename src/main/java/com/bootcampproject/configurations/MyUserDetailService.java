package com.bootcampproject.configurations;

import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        optionalUser.orElseThrow(()->new UsernameNotFoundException("UserName not found"));

        UserDetails userDetails = new MyUserDetails(optionalUser.get());
        /*new AccountStatusUserDetailsChecker().check(userDetails);*/
        return userDetails;
    }
}
