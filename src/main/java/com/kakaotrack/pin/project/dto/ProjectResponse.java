package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ProjectResponse {
    private final Long id;
    private final String title;
    private final int totalRange;   // 같은 프로젝트 id의 range 합계
    private final LocalDate deadline;
    private final List<Integer> departments;
    private final Integer status;
    private final Integer difficult;
    private final String description;

    public ProjectResponse(Project project) {
        this.id = project.getProjectId();
        this.title = project.getTitle();
        // Project에 fields 선언해서 getFields 사용 가능
        this.totalRange = project.getFields().stream()
                .mapToInt(Field::getRange)  // range를 int 형으로
                .sum(); // 합계
        this.departments = project.getFields().stream()
                .map(Field::getDepartment)
                .collect(Collectors.toList());
        this.deadline = project.getDeadline();
        this.status = project.getStatus();
        this.difficult = project.getDifficult();
        this.description = project.getDescription();
    }
}
