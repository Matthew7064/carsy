package com.carsy.repository;

import com.carsy.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
