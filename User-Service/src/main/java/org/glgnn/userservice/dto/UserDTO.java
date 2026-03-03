package org.glgnn.userservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String role; // STUDENT veya TEACHER
}