package com.bootcampproject.configurations;

import com.bootcampproject.entities.User;
import com.bootcampproject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user==null)
        {
            throw new UsernameNotFoundException("Username not found");
        }
        user.setGrantedAuthorities(user.getRoles().stream().map(role -> role.getAuthority())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        return user;
    }
}
