package com.carsy.model.car;

import com.carsy.model.Location;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String vin;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @NotBlank
    @Column(nullable = false)
    private String brand;

    private String model;
    private int year;

    @NotNull
    @Column(nullable = false)
    private BigDecimal rentalPricePerDay;

    private BigDecimal value;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    private int mileage;
    private int horsepower;

    @NotNull
    @Column(nullable = false)
    private LocalDate registrationDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate insuranceExpiryDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate inspectionExpiryDate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("time ASC")
    private List<Location> locations = new ArrayList<>();

    private boolean synchronizedFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id != null && id.equals(car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Car() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank String getVin() {
        return vin;
    }

    public void setVin(@NotBlank String vin) {
        this.vin = vin;
    }

    public @NotBlank String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(@NotBlank String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public @NotBlank String getBrand() {
        return brand;
    }

    public void setBrand(@NotBlank String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public @NotNull BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(@NotNull BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public @NotNull CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(@NotNull CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public @NotNull Fuel getFuel() {
        return fuel;
    }

    public void setFuel(@NotNull Fuel fuel) {
        this.fuel = fuel;
    }

    public @NotNull Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(@NotNull Transmission transmission) {
        this.transmission = transmission;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public @NotNull LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(@NotNull LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public @NotNull LocalDate getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(@NotNull LocalDate insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public @NotNull LocalDate getInspectionExpiryDate() {
        return inspectionExpiryDate;
    }

    public void setInspectionExpiryDate(@NotNull LocalDate inspectionExpiryDate) {
        this.inspectionExpiryDate = inspectionExpiryDate;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public boolean isSynchronizedFlag() {
        return synchronizedFlag;
    }

    public void setSynchronizedFlag(boolean synchronizedFlag) {
        this.synchronizedFlag = synchronizedFlag;
    }
}
