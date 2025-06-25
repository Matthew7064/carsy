package com.carsy.controller;

import com.carsy.model.Order;
import com.carsy.model.car.Car;
import com.carsy.model.car.CarStatus;
import com.carsy.repository.CarRepository;
import com.carsy.repository.OrderRepository;
import com.carsy.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CarRepository carRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, CarRepository carRepository, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID id) {
        Order order = orderService.findOrder(id);
        if (order != null) return ResponseEntity.ok(order);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody @Valid Order order) {
        orderService.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> editOrder(@PathVariable("id") UUID id, @RequestBody @Valid Order order) {
        Order updatedOrder = orderService.editOrder(order, id);
        if (updatedOrder != null) return ResponseEntity.ok(updatedOrder);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") UUID id, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(order, id);
        if (updatedOrder != null) return ResponseEntity.ok(updatedOrder);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable("id") UUID id) {
        orderService.removeOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/{id}/return")
    public ResponseEntity<String> returnCar(@PathVariable("id") UUID orderId) {
        Order order = orderService.findOrder(orderId);
        Car car = order.getCar();
        car.setCarStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
        order.setPaid(true);
        orderRepository.save(order);
        return ResponseEntity.ok("Car returned and order completed.");
    }
}
