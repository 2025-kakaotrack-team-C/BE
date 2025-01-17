package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.project.repository.FieldRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ProjectViewResponse {
    private Long id;
    private String title;
    private String description;
    private Integer difficult;
    private LocalDate deadline;
    private Integer status;
    private LocalDateTime createdAt;
    private List<FieldResponse> fields;
    private UserResponse user;

    public ProjectViewResponse(Long id, String title, String description, Integer difficult, LocalDate deadline, Integer status, LocalDateTime createdAt,
                               List<FieldResponse> fields, UserResponse user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
        this.status = status;
        this.createdAt = createdAt;
        this.fields = fields;
        this.user = user;
    }
}
