package org.sliitprojectspring.admin.controller;


import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.property.dto.PropertyResponceDto;
import org.sliitprojectspring.user.dto.UserCreatDto;
import org.sliitprojectspring.user.dto.UserResponseDto;
import org.sliitprojectspring.admin.service.AdminService;
import org.sliitprojectspring.inquire.service.InquireService;
import org.sliitprojectspring.property.service.PropertyService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {


     private final AdminService adminService;

     private final PropertyService propertyService ;


     private final InquireService  inquireService;

     public AdminController( AdminService adminService , PropertyService propertyService , InquireService inquireService )
     {
         this.adminService = adminService;
         this.propertyService =    propertyService;
         this.inquireService = inquireService;
     }

     @DeleteMapping("/inquires/{inquireId}")
     public void deleteInquireById( @PathVariable  Long inquireId) {

         inquireService.deleteInquireById(inquireId);

    




     }

     @GetMapping("/properties")
     public List<PropertyResponceDto> getAllProperties( ) {


         return propertyService.getAllProperties();
     }


    @PostMapping("/user")
    public UserResponseDto createUser(@RequestBody UserCreatDto dto) {

        return adminService.createUser(dto);




    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers( ) {

         return adminService.getAllUser();
    }

    @DeleteMapping("/user/{userId}")

     public void deleteUser(  @PathVariable Long userId ) {

         adminService.removeUserById( userId );

    }





    @DeleteMapping("/property/{propertyId}")
    public void deleteProperty(@PathVariable Long propertyId) throws java.io.IOException {
        propertyService.deleteProperty(propertyId);
    }

}


