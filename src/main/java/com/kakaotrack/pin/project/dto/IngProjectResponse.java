package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.review.entity.Project_Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 진행중인 프로젝트의 데이터 반환
@Getter
@NoArgsConstructor
public class IngProjectResponse {
    private ProjectResponse project;
    private List<ProjectMemberResponse> projectMembers;

    public IngProjectResponse(ProjectResponse project, List<ProjectMemberResponse> projectMembers){
        this.project = project;

        this.projectMembers = projectMembers;
    }

}
