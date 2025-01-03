package org.example.userservice.service;

import org.example.userservice.models.Role;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.example.userservice.repository.TokenRepository;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Optional;

@Service
public class UserService {

    private final TokenRepository tokenRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String email, String username, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(username);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findUsersByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found for"+ email);
        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())) {
            throw new UsernameNotFoundException("Password mismatch");
        }
        Token token = generatToken(user);
        return tokenRepository.save(token);
    }

    private Token generatToken(User user) {
        Token token = new Token();
        token.setToken(RandomStringUtils.randomAlphanumeric(10));
        token.setExpiryDate(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        token.setUser(user);
        return token;
    }

    public User validateToken(String token) {
        Optional<Token> tokenResult =
                tokenRepository.findByTokenAndExpiryDateGreaterThan
                        (token,System.currentTimeMillis()+3600000);
        if(tokenResult.isEmpty()) {
            throw new UsernameNotFoundException("Invalid token");
        }
        return tokenResult.get().getUser();

    }
}
