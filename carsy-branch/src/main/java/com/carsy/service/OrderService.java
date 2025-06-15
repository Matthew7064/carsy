package com.carsy.service;

import com.carsy.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void addOrder(Order order);
    Order editOrder(Order order, UUID id);
    Order updateOrder(Order order, UUID id);
    List<Order> findAllOrders();
    void removeOrder(UUID id);
    Order findOrder(UUID id);
}
