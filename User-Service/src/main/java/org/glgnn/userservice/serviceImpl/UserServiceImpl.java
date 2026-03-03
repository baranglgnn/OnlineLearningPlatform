package org.glgnn.userservice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.glgnn.userservice.dto.LoginDTO;
import org.glgnn.userservice.dto.UserDTO;
import org.glgnn.userservice.entity.User;
import org.glgnn.userservice.repository.UserRepository;
import org.glgnn.userservice.security.JwtUtil;
import org.glgnn.userservice.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    @Override
    public String login(LoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return JwtUtil.generateToken(user);
    }
}