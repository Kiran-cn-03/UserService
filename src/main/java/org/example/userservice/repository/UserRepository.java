package org.example.userservice.repository;

import jakarta.transaction.Transactional;
import org.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByEmail(String email);
}