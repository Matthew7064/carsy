package com.carsy.repository;

import com.carsy.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
