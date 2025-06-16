package com.carsy.repository;

import com.carsy.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByRoles_Id(UUID id);
    List<User> findAllBySynchronizedFlag(boolean isSynchronized);
}
