package com.carsy.repository;

import com.carsy.model.Branch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {}
