package org.onlinelearning.userservice.serviceImpl;

import org.onlinelearning.userservice.config.JwtUtil;
import org.onlinelearning.userservice.dto.*;
import org.onlinelearning.userservice.entity.User;
import org.onlinelearning.userservice.repository.UserRepository;
import org.onlinelearning.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDTO registerUser(RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .fullName(registerDTO.getFullName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(registerDTO.getRole())
                .isActive(true)
                .build();
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(User::getIsActive)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getIsActive()) throw new RuntimeException("User is inactive");
        return mapToDTO(user);
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getIsActive()) throw new RuntimeException("User is inactive");

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public void hardDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getIsActive()) throw new RuntimeException("User is inactive");

        if (updateUserDTO.getUsername() != null && !updateUserDTO.getUsername().isBlank())
            user.setUsername(updateUserDTO.getUsername());
        if (updateUserDTO.getFullName() != null && !updateUserDTO.getFullName().isBlank())
            user.setFullName(updateUserDTO.getFullName());

        return mapToDTO(userRepository.save(user));
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}