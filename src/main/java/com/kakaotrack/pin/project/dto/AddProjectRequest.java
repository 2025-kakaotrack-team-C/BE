package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddProjectRequest {

    private Long userId;
    private String title;
    private String description;
    private Integer difficult;
    private LocalDate deadline;
    private Integer status;

    @Getter
    private List<AddFieldRequest> fields;

    // Project 빌드
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
