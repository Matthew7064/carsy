package com.carsy.model;

import com.carsy.model.car.Car;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private boolean isPaid;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    private boolean synchronizedFlag;

    public Order() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public @NotNull LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public @NotNull LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    public boolean isSynchronizedFlag() {
        return synchronizedFlag;
    }

    public void setSynchronizedFlag(boolean synchronizedFlag) {
        this.synchronizedFlag = synchronizedFlag;
    }
}
