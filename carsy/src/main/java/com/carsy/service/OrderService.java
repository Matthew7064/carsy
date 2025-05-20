package com.carsy.service;

import com.carsy.model.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);
    Order editOrder(Order order, long id);
    Order updateOrder(Order order, long id);
    List<Order> findAllOrders();
    void removeOrder(long id);
    Order findOrder(long id);
}
