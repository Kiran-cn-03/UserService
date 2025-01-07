package org.example.userservice.security.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.userservice.models.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JsonDeserialize
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUsersByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + username + " doesn't exist");
        }
        return new CustomUserDetails(optionalUser.get());
    }
}
