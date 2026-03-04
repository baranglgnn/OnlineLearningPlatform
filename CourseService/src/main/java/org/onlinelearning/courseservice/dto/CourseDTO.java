package org.onlinelearning.courseservice.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private Double price;
    private Boolean isActive;
    private LocalDateTime createdAt;
}