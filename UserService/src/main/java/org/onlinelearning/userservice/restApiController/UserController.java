package org.onlinelearning.userservice.restApiController;

import org.onlinelearning.userservice.dto.*;
import org.onlinelearning.userservice.entity.Role;
import org.onlinelearning.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(userService.registerUser(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("getUserById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserDTO));
    }

    @DeleteMapping("/softDelete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok("User soft deleted");
    }

    @DeleteMapping("/hardDelete/{id}")
    public ResponseEntity<String> hardDelete(@PathVariable Long id) {
        if (!getCurrentUserRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can hard delete");
        }
        userService.hardDeleteUser(id);
        return ResponseEntity.ok("User hard deleted");
    }

    private Role getCurrentUserRole() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());
    }
}