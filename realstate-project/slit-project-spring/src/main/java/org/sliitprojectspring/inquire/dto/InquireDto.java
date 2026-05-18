package org.sliitprojectspring.inquire.dto;

import java.util.List;

public record InquireDto(
        Long id,
        Long userId,
        Long ownerId,
        String ownerName,
        String propertyId,
        String propertyDetails,
        String description,
        String name,
        String contactNumber,
        String reply,
        List<MessageDto> messages,
        boolean isRead
) {
}
