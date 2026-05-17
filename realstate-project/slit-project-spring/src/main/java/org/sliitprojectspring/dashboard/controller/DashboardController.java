package org.sliitprojectspring.dashboard.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sliitprojectspring.property.dto.PropertyResponceDto;
import org.sliitprojectspring.property.service.PropertyService;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {




    private final PropertyService propertyService;

    public DashboardController ( PropertyService propertyService ) {
        this.propertyService = propertyService;
    }

    @GetMapping()
    public List<PropertyResponceDto> getAllPropeties() {
         return propertyService.getAllProperties();
    }









}
