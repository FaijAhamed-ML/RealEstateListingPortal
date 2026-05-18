package org.sliitprojectspring.review.dto;

import java.time.LocalDateTime;

public record ReviewDto(
    Long id,
    String comment,
    int rating,
    Long propertyId,
    Long userId,
    String userName,
    LocalDateTime createdAt
) {}
