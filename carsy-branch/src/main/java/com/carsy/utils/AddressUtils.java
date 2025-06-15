package com.carsy.utils;

import com.carsy.model.Address;

public class AddressUtils {
    public static void updateAddress(Address source, Address target) {
        target.setStreet(source.getStreet());
        target.setNumber(source.getNumber());
        if (source.getFlatNumber() != null) target.setFlatNumber(source.getFlatNumber());
        target.setPostalCode(source.getPostalCode());
        target.setCity(source.getCity());
        target.setCountry(source.getCountry());
    }
}
