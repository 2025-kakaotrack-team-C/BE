package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.review.entity.Project_Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class ProjectMemberResponse {
    private final Long memberId;
    private final Integer department;

    public ProjectMemberResponse(Long memberId, Integer department) {
        this.memberId = memberId;
        this.department = department;
    }

}
