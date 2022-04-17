package com.bootcampproject.configurations;


import com.bootcampproject.entities.User;
import com.bootcampproject.exceptions.AccountLockedException;
import com.bootcampproject.exceptions.UserDeactivateException;
import com.bootcampproject.repositories.UserRepo;
import com.bootcampproject.services.SimpleMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

// customize error k lie hume ye krna hoga.
@Slf4j
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private SimpleMailService mailService;

    /*
        @Override
        protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {*/
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        System.out.println("SEM>>>>>>>");
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();
        System.out.println(presentedPassword);
        String principal = authentication.getPrincipal().toString();
        User user = userRepo.findByEmail(principal);
        if (user != null) {
            if (user.isLocked()) {
                throw new AccountLockedException("Your Account is locked.Please contact with support team!");
            }
            if (!user.isActive()) {
                throw new UserDeactivateException("Your Account is de-activated");
            }
            if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {
                logger.debug("Authentication failed: password does not match stored value");

                int temp = user.getInvalidAttemptCount() != null ? user.getInvalidAttemptCount() : 0;
                user.setInvalidAttemptCount(++temp);
                if (temp == 3 && (user.getRoles().stream().noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN")))) {
                    //if (temp == 3 && (!user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")))) {
                    user.setLocked(true);


                    mailService.sendMail(user.getEmail(), "Your account has been locked now", "Hi, Your account has been locked due to maximum attempt of login!\nTo activate your account please contact Admin via admin@gmail.com");

                } else {
                    user.getRoles().forEach(role -> log.info(role.getAuthority()));
                }


                userRepo.save(user);

                throw new BadCredentialsException(
                        "Password and email doesn't match");
            }
        }
        Authentication auth = super.authenticate(authentication);
        return auth;
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        System.out.println("SUCC>>>");
        User user1 = userRepo.findByEmail(user.getUsername());
        if (user1 != null && user1.getInvalidAttemptCount() < 3) {
            user1.setInvalidAttemptCount(0);
            userRepo.save(user1);
        }

        return super.createSuccessAuthentication(principal, authentication, user);
    }
}