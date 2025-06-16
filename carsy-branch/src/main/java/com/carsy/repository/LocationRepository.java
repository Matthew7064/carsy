package com.carsy.repository;

import com.carsy.model.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> findAllBySynchronizedFlag(boolean isSynchronized);
}
