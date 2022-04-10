package com.bootcampproject.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private AccessTokenFilter accessTokenFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager AuthenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()*//*.antMatchers("/admin/**").hasRole("ADMIN")*//*
                .antMatchers("/seller/**").hasRole("SELLER")
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .antMatchers("/**").permitAll().and().csrf().disable();
    }*/
@Override
public void configure(HttpSecurity httpSecurity) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry sc =
            httpSecurity.csrf().disable()
                    .authorizeRequests().antMatchers("/**").permitAll(); // jispe @preauthorized annotation ni h vo sabko accessible h
    sc.anyRequest().authenticated().and().
            exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.cors();
}
}
