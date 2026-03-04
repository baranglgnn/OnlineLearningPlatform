    package org.onlinelearning.userservice.dto;

    import lombok.*;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UpdateUserDTO {
        private String username;
        private String fullName;
    }