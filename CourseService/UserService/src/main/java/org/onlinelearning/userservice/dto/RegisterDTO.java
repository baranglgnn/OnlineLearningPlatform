package org.onlinelearning.userservice.dto;

import org.onlinelearning.userservice.entity.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private Role role;
}