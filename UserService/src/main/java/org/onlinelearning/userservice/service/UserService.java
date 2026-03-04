package org.onlinelearning.userservice.service;

import org.onlinelearning.userservice.dto.*;
import java.util.List;

public interface UserService {

    UserDTO registerUser(RegisterDTO registerDTO);
    LoginResponseDTO login(LoginDTO loginDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);

    void softDeleteUser(Long id);
    void hardDeleteUser(Long id);

    // Yeni: güncelleme
    UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO);
}