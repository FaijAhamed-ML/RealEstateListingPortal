package org.sliitprojectspring.booking.dto;

import java.time.LocalDateTime;

public record BookingDto(
    Long id,
    Long userId,
    String userName,
    Long ownerId,
    Long propertyId,
    LocalDateTime preferredDate,
    String status,
    String message,
    LocalDateTime createdAt
) {}
