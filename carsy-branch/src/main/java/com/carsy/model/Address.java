package com.carsy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String street;

    @NotBlank
    @Column(nullable = false)
    private String number;

    private String flatNumber;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be in format: XX-XXX")
    private String postalCode;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Column(nullable = false)
    private String country;

    public Address() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank String getStreet() {
        return street;
    }

    public void setStreet(@NotBlank String street) {
        this.street = street;
    }

    public @NotBlank String getNumber() {
        return number;
    }

    public void setNumber(@NotBlank String number) {
        this.number = number;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public @NotBlank @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be in format: XX-XXX") String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NotBlank @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be in format: XX-XXX") String postalCode) {
        this.postalCode = postalCode;
    }

    public @NotBlank String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public @NotBlank String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank String country) {
        this.country = country;
    }
}
