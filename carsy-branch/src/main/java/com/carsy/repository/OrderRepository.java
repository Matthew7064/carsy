package com.carsy.repository;

import com.carsy.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllBySynchronizedFlag(boolean isSynchronized);
}
