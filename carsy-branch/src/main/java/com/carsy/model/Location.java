package com.carsy.model;

import com.carsy.model.car.Car;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private BigDecimal latitude;

    @NotNull
    @Column(nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Car car;

    private boolean synchronizedFlag;

    public Location() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(@NotNull BigDecimal latitude) {
        this.latitude = latitude;
    }

    public @NotNull BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(@NotNull BigDecimal longitude) {
        this.longitude = longitude;
    }

    public @NotNull LocalDateTime getTime() {
        return time;
    }

    public void setTime(@NotNull LocalDateTime time) {
        this.time = time;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isSynchronizedFlag() {
        return synchronizedFlag;
    }

    public void setSynchronizedFlag(boolean synchronizedFlag) {
        this.synchronizedFlag = synchronizedFlag;
    }
}
