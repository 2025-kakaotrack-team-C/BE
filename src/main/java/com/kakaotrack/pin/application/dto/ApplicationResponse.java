package com.kakaotrack.pin.application.dto;

import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationResponse {
    private Long applicationId;
    private MemberDTO member;
    private ProjectDTO project;
    private Integer department;
    private Integer status;

    public ApplicationResponse(Application application) {
        this.applicationId = application.getApplicationId();
        this.member = new MemberDTO(application.getAppMember());
        this.project = new ProjectDTO(application.getAppProject());
        this.department = application.getDepartment();
        this.status = application.getStatus();
    }
}
