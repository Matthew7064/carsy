package com.carsy.repository;

import com.carsy.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRole(String role);
}
