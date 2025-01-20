package com.kakaotrack.pin.application.dto;

import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AddApplicationRequest {

    private Long userId;
    private Long projectId;
    private Integer department;
    private Integer status;

    public Application toEntity(Member member, Project project) {
        return Application.builder()
                .appMember(member)
                .appProject(project)
                .department(department)
                .status(status)
                .build();
    }
}
