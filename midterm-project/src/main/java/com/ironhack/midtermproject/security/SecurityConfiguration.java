package com.ironhack.midtermproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests()
                //Route to create a new checking account
                .antMatchers(HttpMethod.POST, "/accounts").hasRole("ADMIN")
                //Route to create a new credit card account
                .antMatchers(HttpMethod.POST, "/credit-cards").hasRole("ADMIN")
                //Route to create a new savings account
                .antMatchers(HttpMethod.POST, "/savings-accounts").hasRole("ADMIN")
                //Route to get a checking account by id
                //This route will let you access to all the information of this checking account, including
                //the balance.
                .antMatchers(HttpMethod.GET, "/checking-accounts/**")
                .hasAnyRole("ADMIN", "ACCOUNT-HOLDER")
                //Route to get a credit card account by id
                //This route will let you access to all the information of this credit card,
                //including the balance
                .antMatchers(HttpMethod.GET, "/credit-cards/**")
                .hasAnyRole("ADMIN", "ACCOUNT-HOLDER")
                //Route to get a savings account by id
                //This route will let you access to all the information of this savings account,
                //including the balance
                .antMatchers(HttpMethod.GET, "/savings-accounts/**")
                .hasAnyRole("ADMIN", "ACCOUNT-HOLDER")
                //Route to get a student checking account by id
                //This route will let you access to all the information of this student checking
                //account, including the balance
                .antMatchers(HttpMethod.GET, "/student-checking-accounts/**")
                .hasAnyRole("ADMIN", "ACCOUNT-HOLDER")
                //Route to update the balance of any type of account
                .antMatchers(HttpMethod.PATCH, "/accounts/**/balance/").hasRole("ADMIN")
                .anyRequest().permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder().username("Admin").password("password1")
                .authorities("AUT1").roles("ADMIN").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("AccountHolder").password("password2")
                .authorities("AUT2").roles("ACCOUNT-HOLDER").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
