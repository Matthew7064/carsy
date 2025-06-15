package com.carsy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record LocationDTO(
        UUID id,
        UUID carId,
        BigDecimal latitude,
        BigDecimal longitude,
        LocalDateTime time
) {}
