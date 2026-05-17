package org.sliitprojectspring.common.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {


    @Value("${file.upload-path}")
    private String uploadDir ;

    public  String saveImage(MultipartFile file) throws IOException{

        // validate file type
        String contentType = file.getContentType();
        if( contentType == null || ( !contentType.equals("image/jpeg" ) && !contentType.equals("image/png"))) {


            throw  new IllegalArgumentException("Only JPEG and PNG are allowed");


        }


        // create directories if they do not exists

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if( !Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }


        // generate unique file name
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + extension;
        // Save file
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


        System.out.println(filePath.toString());

        return newFilename;






    }

    public void deleteImage(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        Files.deleteIfExists(filePath);
    }





}
