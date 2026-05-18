package org.sliitprojectspring.property.dto;

import java.util.List;

public record PropertyResponceDto(



        Long  userId ,
        String description ,
        Long propertyId ,

        String details  ,

        String  location ,

        String contactNumber  ,


        double value ,

        List<String> imagePaths

) {
}
