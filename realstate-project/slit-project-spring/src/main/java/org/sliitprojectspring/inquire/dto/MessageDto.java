package org.sliitprojectspring.inquire.dto;

import java.time.LocalDateTime;

public record MessageDto(
    Long id,
    Long senderId,
    String content,
    LocalDateTime timestamp
) {}
