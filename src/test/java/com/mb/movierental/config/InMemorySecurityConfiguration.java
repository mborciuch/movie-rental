package com.mb.movierental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//For reference Only
//@Profile("security-in-memory")
//@Configuration
public class InMemorySecurityConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    return http.build();
  }

    @Bean
    public UserDetailsService users() {
    UserDetails user =
        User.builder()
            .username("user")
            .password("{noop}password")
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(user);
    }
}
