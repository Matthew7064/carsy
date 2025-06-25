package com.carsy.repository;

import com.carsy.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByAccountNumber(String accountNumber);
    User findByLogin(String login);
}
