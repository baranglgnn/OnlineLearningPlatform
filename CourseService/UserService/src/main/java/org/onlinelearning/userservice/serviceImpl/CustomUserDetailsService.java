package org.onlinelearning.userservice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.onlinelearning.userservice.entity.User;
import org.onlinelearning.userservice.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getIsActive(), // account enabled
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}