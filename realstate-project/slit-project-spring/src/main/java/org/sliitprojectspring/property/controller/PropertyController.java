package org.sliitprojectspring.property.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.property.dto.CreatePropertyDto;
import org.sliitprojectspring.inquire.dto.InquireDto;
import org.sliitprojectspring.property.dto.PropertyResponceDto;
import org.sliitprojectspring.inquire.service.InquireService;
import org.sliitprojectspring.property.service.PropertyService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/property")
@CrossOrigin("*")

public class PropertyController {


    private final PropertyService propertyService ;

    private final InquireService inquireService ;


    public PropertyController( PropertyService propertyService, InquireService inquireService) {
        this.inquireService  = inquireService;
        this.propertyService = propertyService ;
    }


    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public PropertyResponceDto createProperty(@ModelAttribute CreatePropertyDto dto  )  throws IOException {



        return propertyService.createProperty(dto);




    }

    @GetMapping("/{propertyId}/inquires")

    public List<InquireDto> getInquiersByPropertyId(@PathVariable Long propertyId) {


        return inquireService.getAllInquiresByPropertyId(propertyId);

    }

    @GetMapping("/filterProperties")


    public List<PropertyResponceDto>   sortProperties( @RequestParam  double minPrice ,@RequestParam double maxPrice ){


                return propertyService.sortProperties(minPrice , maxPrice);





    }

    @GetMapping("/{userId}")

    public List<PropertyResponceDto> getPropertiesByUserId(@PathVariable Long  userId) {

        return propertyService.getPropertiesByUserId(  userId);

    }
    @PutMapping(value = "/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PropertyResponceDto updateProperty(
            @PathVariable Long propertyId,
            @ModelAttribute CreatePropertyDto dto) throws IOException {
        return propertyService.updateProperty(propertyId, dto);
    }
    @DeleteMapping("/{propertyId}")
    public void deleteProperty(@PathVariable Long propertyId) throws IOException {
        propertyService.deleteProperty(propertyId);
    }
}
