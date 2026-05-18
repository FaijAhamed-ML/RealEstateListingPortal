package org.sliitprojectspring.inquire.dto;

public record ReplyRequest(
    String content,
    Long senderId
) {}
