package com.bootcampproject.configurations;

import com.bootcampproject.entities.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/*public class AuditorAwareImplementation implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Mohd Kashif");
    }
}*/
/*
class AuditorAwareImplementation implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated())
            return Optional.ofNullable(getCurrentUserEmail());
        return Optional.ofNullable(((User) authentication.getPrincipal()).getEmail());
    }

        */
/*return Optional.ofNullable(SecurityContextHolderUtil.getCurrentUserEmail());*//*

    }*/
public class AuditorAwareImplementation implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null)
        {
            return Optional.of("BOOTSTRAP DATA");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails)
            return Optional.of(((UserDetails)principal).getUsername());
        return Optional.of("SELF REGISTERED");
    }
}