package org.sliitprojectspring.user.controller;

import org.springframework.web.bind.annotation.*;
import org.sliitprojectspring.user.dto.AuthDto;
import org.sliitprojectspring.property.dto.PropertyResponceDto;
import org.sliitprojectspring.user.dto.UserCreatDto;
import org.sliitprojectspring.user.dto.UserResponseDto;
import org.sliitprojectspring.inquire.service.InquireService;
import org.sliitprojectspring.property.service.PropertyService;
import org.sliitprojectspring.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final InquireService inquireService;
    private final PropertyService propertyService;

    public UserController(UserService userService, InquireService inquireService, PropertyService propertyService) {
        this.userService = userService;
        this.inquireService = inquireService;
        this.propertyService = propertyService;
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody AuthDto authDto) {
        return userService.login(authDto.username(), authDto.password());
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserCreatDto userCreatDto) {
        return userService.register(userCreatDto);
    }

    @DeleteMapping("/{inquireId}")
    public boolean deleteAnInquire(@PathVariable Long inquireId) {
        return inquireService.deleteInquire(inquireId);
    }

    @GetMapping("/property/{userId}")
    public List<PropertyResponceDto> getAllPropertiesByUserId(@PathVariable Long userId) {
        return propertyService.getPropertiesByUserId(userId);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/role/{role}")
    public List<UserResponseDto> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }
}
