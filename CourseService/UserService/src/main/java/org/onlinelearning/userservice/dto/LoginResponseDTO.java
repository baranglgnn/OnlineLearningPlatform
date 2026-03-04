package org.onlinelearning.userservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String email;
    private String username;
    private String fullName;
    private String role;
}