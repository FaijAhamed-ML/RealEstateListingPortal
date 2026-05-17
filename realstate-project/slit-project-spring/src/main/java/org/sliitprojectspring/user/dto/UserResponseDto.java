package org.sliitprojectspring.user.dto;

import java.util.UUID;

public record UserResponseDto ( Long  id ,
                              String name ,
                              String role ,
                              String email  ,
                              String phone) {
}
