package com.carsy.repository;

import com.carsy.model.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {}
