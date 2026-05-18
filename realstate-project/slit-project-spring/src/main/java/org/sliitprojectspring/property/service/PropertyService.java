package org.sliitprojectspring.property.service;

import org.sliitprojectspring.common.service.FileStorageService;

import org.springframework.stereotype.Service;
import org.sliitprojectspring.property.dto.CreatePropertyDto;
import org.sliitprojectspring.property.dto.PropertyResponceDto;
import org.sliitprojectspring.user.exception.UserNotFoundException;
import org.sliitprojectspring.property.model.Property;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.inquire.repository.InquireRepository;
import org.sliitprojectspring.property.repository.PropertyRepository;
import org.sliitprojectspring.user.repository.UserRepository;
import org.sliitprojectspring.common.utils.SortHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PropertyService {


    private final PropertyRepository propertyRepository ;

    private final FileStorageService fileStorageService ;
    private final UserRepository userRepository;

    private final SortHelper sortHelper ;
    private final InquireRepository inquireRepository;

    public PropertyService( PropertyRepository propertyRepository , FileStorageService fileStorageService, UserRepository userRepository ,SortHelper sortHelper,
                            InquireRepository inquireRepository)
    {
        this.fileStorageService = fileStorageService;
        this.sortHelper = sortHelper;
         this.propertyRepository = propertyRepository;

         this.userRepository =  userRepository;
        this.inquireRepository = inquireRepository;
    }

    public List<PropertyResponceDto> getPropertiesByUserId( Long  userId){


         userRepository.findById( userId).orElseThrow(

                 () -> new UserNotFoundException(" use is not  found ")
         );


         return propertyRepository.getPropertiesByUserId(userId)
                 .stream().map(
                         properties ->
                                  new PropertyResponceDto(
                                          properties.getUser().getId(),
                                          properties.getDescription(),
                                          properties.getId(),
                                          properties.getDetails(),
                                          properties.getLocation(),
                                          properties.getContactNumber(),
                                          properties.getValue(),
                                          properties.getImagePaths()




                                  )
                 ).collect(Collectors.toList());


    }


     public PropertyResponceDto createProperty( CreatePropertyDto dto )  throws IOException {

         User user = userRepository.findById(dto.userId())
                 .orElseThrow(() -> new RuntimeException("User not found"));

         List<String> imagePaths = dto.images().stream().map(image -> {
             try {
                 return fileStorageService.saveImage(image);
             } catch (IOException e) {
                 throw new RuntimeException("Failed to save image", e);
             }
         }).collect(Collectors.toList());

         Property property = new Property() ;
         property.setCreatedAt(LocalDateTime.now());
         property.setDescription( dto.description());
         property.setDetails( dto.details());
         property.setContactNumber(dto.contactNumber());
         property.setLocation( dto.location());


         property.setUser( user);

         property.setImagePaths( imagePaths);
         property.setValue( dto.value() );


         propertyRepository.save( property);

          return new PropertyResponceDto( property.getUser().getId() , property.getDescription()   ,
                  property.getId() ,property.getDetails() , property.getLocation() , property.getContactNumber() , property.getValue()  , property.getImagePaths()) ;


     }

     public List<PropertyResponceDto> getAllProperties() {

        return  propertyRepository.findAll().stream().map( property ->  new PropertyResponceDto(
                property.getUser().getId(),
                property.getDescription(),
                property.getId(),
                property.getDetails(),
                property.getLocation(),
                property.getContactNumber(),
                property.getValue(),
                property.getImagePaths()


        )).collect(Collectors.toList());
     }
     public List<PropertyResponceDto> sortProperties( double minPrice , double maxPrice ){


        List<Property> properties= propertyRepository.getPropertiesByValueBetween(minPrice , maxPrice);



        var sortedProperties = sortHelper.sortProperties(properties);


         return sortedProperties.stream().map(


                 property ->
                         new PropertyResponceDto(
                                 property.getUser().getId(),
                                 property.getDescription(),
                                 property.getId(),
                                 property.getDetails(),
                                 property.getLocation(),
                                 property.getContactNumber(),
                                 property.getValue(),
                                 property.getImagePaths()

                         )
         ).collect(Collectors.toList());






     }
    public PropertyResponceDto updateProperty(Long propertyId, CreatePropertyDto dto) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setDescription(dto.description());
        property.setDetails(dto.details());
        property.setLocation(dto.location());
        property.setContactNumber(dto.contactNumber());
        property.setValue(dto.value());
        property.setUpdateAt(LocalDateTime.now());

        if (dto.images() != null && !dto.images().isEmpty()) {
            List<String> newImagePaths = dto.images().stream().map(image -> {
                try {
                    return fileStorageService.saveImage(image);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image", e);
                }
            }).collect(Collectors.toList());
            property.setImagePaths(newImagePaths);
        }

        propertyRepository.save(property);

        return new PropertyResponceDto(
                property.getUser().getId(),
                property.getDescription(),
                property.getId(),
                property.getDetails(),
                property.getLocation(),
                property.getContactNumber(),
                property.getValue(),
                property.getImagePaths()
        );
    }

    public void deleteProperty(Long propertyId) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Delete images from storage
        if (property.getImagePaths() != null) {
            for (String path : property.getImagePaths()) {
                fileStorageService.deleteImage(path);
            }
        }

        propertyRepository.delete(property);
    }
}
