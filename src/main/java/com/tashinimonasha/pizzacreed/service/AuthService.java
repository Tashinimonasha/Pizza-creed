package com.tashinimonasha.pizzacreed.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    @Bean
    BCryptPasswordEncoder passwordEncoder();
}
