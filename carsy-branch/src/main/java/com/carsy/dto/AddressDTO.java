package com.carsy.dto;

import java.util.UUID;

public record AddressDTO(
        UUID id,
        String street,
        String number,
        String flatNumber,
        String city,
        String postalCode,
        String country
) {}
