package org.sliitprojectspring.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreatDto(



        @NotBlank
        String name ,

        @NotBlank
        String username ,
        @NotBlank
        String password ,
        @NotBlank
        String phoneNumber ,
        @NotBlank
        String role  ,
        @Email
        String email
) {



}
