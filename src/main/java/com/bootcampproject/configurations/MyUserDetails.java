package com.bootcampproject.configurations;

import com.bootcampproject.entities.Role;
import com.bootcampproject.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MyUserDetails extends User implements UserDetails {

    @Autowired
    private Role role;

    public MyUserDetails(User user) {
        super(user);
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*        List<Role> roleList = super.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        log.info(String.valueOf(grantedAuthorities));
        return grantedAuthorities;*/


        List<GrantedAuthority> grantedAuthorities = super.getRoles().stream().map(role -> role.getAuthority())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return grantedAuthorities;

/*        return super.getRole().stream().map(role -> role.getAuthority())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
