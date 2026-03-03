package org.glgnn.userservice.service;

import org.glgnn.userservice.dto.LoginDTO;
import org.glgnn.userservice.dto.UserDTO;
import org.glgnn.userservice.entity.User;

public interface UserService {
    User register(UserDTO dto);
    String login(LoginDTO dto);
}