package org.glgnn.userservice.restApiController;

import lombok.RequiredArgsConstructor;
import org.glgnn.userservice.dto.LoginDTO;
import org.glgnn.userservice.dto.UserDTO;
import org.glgnn.userservice.entity.User;
import org.glgnn.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestApiController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}