package org.example.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> {
            try{
                request.anyRequest().permitAll().and().cors().disable()
                        .csrf().disable();
            }catch (Exception e){
                e.printStackTrace();
            }
    });
        return http.build();
}
}
