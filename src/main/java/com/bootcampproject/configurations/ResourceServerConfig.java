package com.bootcampproject.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableResourceServer
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true) // pre authorize tbi work krti h.. is annotation ki help se
    public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private AccessTokenFilter accessTokenFilter;
        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
        @Autowired
        UserDetailsService userDetailsService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        public ResourceServerConfig() {
            super();
        }


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

        @Bean
        public AuthenticationProvider authenticationProvider(MyUserDetailService userDetailsService) {
            final LoginAuthenticationProvider authenticationProvider = new LoginAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
        }
    }
