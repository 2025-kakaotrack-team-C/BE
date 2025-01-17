package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateProjectRequest {
    private String title;
    private String description;
    private Integer difficult;
    private LocalDate deadline;
    private List<UpdateFieldRequest> fields;

    public UpdateProjectRequest(String title, String description, Integer difficult, LocalDate deadline, List<UpdateFieldRequest> fields) {
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
        this.fields = fields;
    }

}
