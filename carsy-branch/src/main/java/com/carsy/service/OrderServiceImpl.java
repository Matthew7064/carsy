package com.carsy.service;

import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import com.carsy.repository.OrderRepository;
import com.carsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CarRepository carRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order editOrder(Order order, UUID id) {
        Order foundOrder = orderRepository.findById(id).orElse(null);
        if (foundOrder != null) {
            updateCar(order, foundOrder);
            updateUser(order, foundOrder);
            foundOrder.setPaid(order.isPaid());
            foundOrder.setStartDate(order.getStartDate());
            foundOrder.setEndDate(order.getEndDate());
            foundOrder.setPrice(order.getPrice());
            return orderRepository.save(foundOrder);
        }
        return null;
    }

    @Override
    public Order updateOrder(Order order, UUID id) {
        Order foundOrder = orderRepository.findById(id).orElse(null);
        if (foundOrder != null) {
            if (order.getCar() != null) updateCar(order, foundOrder);
            if (order.getUser() != null) updateUser(order, foundOrder);
            foundOrder.setPaid(order.isPaid());
            if (order.getStartDate() != null) foundOrder.setStartDate(order.getStartDate());
            if (order.getEndDate() != null) foundOrder.setEndDate(order.getEndDate());
            if (order.getPrice() != null) foundOrder.setPrice(order.getPrice());
            return orderRepository.save(foundOrder);
        }
        return null;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void removeOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findOrder(UUID id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    private void updateCar(Order order, Order foundOrder) {
        Car car = order.getCar();
        Car attachedCar = carRepository.findById(car.getId()).orElseThrow(() -> new IllegalArgumentException("Car not found, id: " + car.getId()));
        foundOrder.setCar(attachedCar);
    }

    private void updateUser(Order order, Order foundOrder) {
        User user = order.getUser();
        User attachedUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found, id: " + user.getId()));
        foundOrder.setUser(attachedUser);
    }
}
