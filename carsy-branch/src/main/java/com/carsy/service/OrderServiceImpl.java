package com.carsy.service;

import com.carsy.exception.InvalidDataException;
import com.carsy.model.Order;
import com.carsy.model.User;
import com.carsy.model.car.Car;
import com.carsy.repository.CarRepository;
import com.carsy.repository.OrderRepository;
import com.carsy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            foundOrder.setSynchronizedFlag(false);
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
            if (order.getStartDate() != null && order.getEndDate() != null) {
                if (order.getStartDate().isAfter(order.getEndDate())) {
                    throw new InvalidDataException("Start date must not be after end date.");
                }
                foundOrder.setStartDate(order.getStartDate());
                foundOrder.setEndDate(order.getEndDate());
            } else {
                if (order.getStartDate() != null) {
                    if (order.getStartDate().isAfter(foundOrder.getEndDate())) {
                        throw new InvalidDataException("Start date must not be after end date.");
                    }
                    foundOrder.setStartDate(order.getStartDate());
                }
                if (order.getEndDate() != null) {
                    if (order.getEndDate().isBefore(foundOrder.getStartDate())) {
                        throw new InvalidDataException("End date must not be before start date.");
                    }
                    foundOrder.setEndDate(order.getEndDate());
                }
            }
            if (order.getPrice() != null) {
                if (order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new InvalidDataException("Price must be positive.");
                }
                foundOrder.setPrice(order.getPrice());
            }
            foundOrder.setSynchronizedFlag(false);
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
