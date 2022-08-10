package com.testlab2.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.cert.Extension;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.trace("Setting up SecurityFilterChain...");
        httpSecurity.authorizeRequests()
                .antMatchers("/status")
                .hasAuthority("admin")
                .antMatchers("/hello")
                .hasAuthority("admin");

        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout();

        log.trace("Building HttpSecurity to return SecurityFilterChain...");
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.trace("Creating InMemoryUserDetailsManager...");
        InMemoryUserDetailsManager userDetailServiceManager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("test")
                .password(passwordEncoder().encode("test"))
                .authorities("read")
                .build();

        UserDetails adminUser1 = User.withUsername("admin")
                .password(passwordEncoder().encode("test"))
                .authorities("admin")
                .build();

        userDetailServiceManager.createUser(user1);
        userDetailServiceManager.createUser(adminUser1);

        log.trace("Built User Details, Added to UserDetailsManager, returning UserDetailsManager... ");
        return userDetailServiceManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
