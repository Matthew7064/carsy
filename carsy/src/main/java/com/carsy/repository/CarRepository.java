package com.carsy.repository;

import com.carsy.model.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findById(long id);
}
