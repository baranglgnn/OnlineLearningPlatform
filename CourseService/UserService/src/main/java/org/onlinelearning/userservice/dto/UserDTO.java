package org.onlinelearning.userservice.dto;

import lombok.*;
import org.onlinelearning.userservice.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Role role;
}