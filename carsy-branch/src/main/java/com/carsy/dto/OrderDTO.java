package com.carsy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        UUID carId,
        UUID userId,
        boolean isPaid,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price
) {}
