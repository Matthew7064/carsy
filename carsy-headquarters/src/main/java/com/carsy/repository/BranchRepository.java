package com.carsy.repository;

import com.carsy.model.Branch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Transactional
@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {}
