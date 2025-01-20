package com.kakaotrack.pin.application.dto;

import com.kakaotrack.pin.domain.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectDTO {
    private Long projectId;
    private MemberDTO member;

    public ProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.member = new MemberDTO(project.getMember());
    }
}
