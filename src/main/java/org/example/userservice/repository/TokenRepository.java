package org.example.userservice.repository;

import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByTokenAndExpiryDateGreaterThan(String token, long expiryDateIsGreaterThan);
}
