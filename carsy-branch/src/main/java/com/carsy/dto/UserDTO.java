package com.carsy.dto;

import java.util.Set;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String pesel,
        String name,
        String surname,
        String email,
        String phoneNumber,
        String accountNumber,
        String login,
        Set<String> roles,
        AddressDTO address,
        UUID branchId
) {}
