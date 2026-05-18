package org.sliitprojectspring.inquire.dto;

public record InquireRequest(

        Long userId ,
        String propertyId ,
        String  description ,
        String name ,
        String contactNumber,
        Long ownerId) {
}
