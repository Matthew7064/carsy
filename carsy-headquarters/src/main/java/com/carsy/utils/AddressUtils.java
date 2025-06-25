package com.carsy.utils;

import com.carsy.exception.InvalidDataException;
import com.carsy.model.Address;

public class AddressUtils {
    public static void editAddress(Address source, Address target) {
        target.setStreet(source.getStreet());
        target.setNumber(source.getNumber());
        if (source.getFlatNumber() != null) target.setFlatNumber(source.getFlatNumber());
        target.setPostalCode(source.getPostalCode());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
    }

    public static void updateAddress(Address source, Address target) {
        if (source.getStreet() != null && !source.getStreet().isBlank()) {
            target.setStreet(source.getStreet());
        }
        if (source.getNumber() != null && !source.getNumber().isBlank()) {
            target.setNumber(source.getNumber());
        }
        if (source.getFlatNumber() != null && !source.getFlatNumber().isBlank()) {
            target.setFlatNumber(source.getFlatNumber());
        }
        if (source.getPostalCode() != null && !source.getPostalCode().isBlank()) {
            String postalCodeRegex = "\\d{2}-\\d{3}";
            if (!source.getPostalCode().matches(postalCodeRegex)) {
                throw new InvalidDataException("Invalid postal code format.");
            }
            target.setPostalCode(source.getPostalCode());
        }
        if (source.getCity() != null && !source.getCity().isBlank()) {
            target.setCity(source.getCity());
        }
        if (source.getCountry() != null && !source.getCountry().isBlank()) {
            target.setCountry(source.getCountry());
        }
    }
}
