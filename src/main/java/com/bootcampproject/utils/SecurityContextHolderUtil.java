package com.bootcampproject.utils;

import com.bootcampproject.configurations.MyUserDetailService;
import com.bootcampproject.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContextHolderUtil {
    public static String getCurrentUserEmail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        Object principal = authentication.getPrincipal();
       /* if(authentication==null || !authentication.isAuthenticated())
            return "SYSTEM";
        return "LOGGED IN USER";*/
        if (principal instanceof UserDetails)
            return ((UserDetails)principal).getUsername();
        else
        {
            return "Still not logged in";
        }
    }
}