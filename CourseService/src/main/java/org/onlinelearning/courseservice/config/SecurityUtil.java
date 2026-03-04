package org.onlinelearning.courseservice.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class SecurityUtil {

    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getDetails() == null) {
            throw new RuntimeException("Unauthorized");
        }

        Map<String, Object> claims = (Map<String, Object>) authentication.getDetails();

        return (String) claims.get("role");
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getDetails() == null) {
            throw new RuntimeException("Unauthorized");
        }

        Map<String, Object> claims = (Map<String, Object>) authentication.getDetails();

        return ((Number) claims.get("userId")).longValue();
    }

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new RuntimeException("Unauthorized");
        }

        return authentication.getName(); // subject (email)
    }
}