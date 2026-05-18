package org.sliitprojectspring.property.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CreatePropertyDto(



        Long  userId ,
        String description ,


        String contactNumber ,
        String location ,

        String details  ,


        double value ,
        List<MultipartFile> images




) {
}
