package org.sliitprojectspring.review.dto;

public record ReviewRequest(
    String comment,
    int rating,
    Long propertyId,
    Long userId
) {}
