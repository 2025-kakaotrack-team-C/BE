package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Project;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class addProjectRequest {

    private Long userId;
    private String title;
    private String description;
    private Integer difficult;
    private LocalDate deadline;
    private Integer status;


    public Project toEntity() {
        return Project.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .difficult(difficult)
                .deadline(deadline)
                .status(status)
                .build();
    }

}
