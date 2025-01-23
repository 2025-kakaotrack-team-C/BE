package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.review.entity.Project_Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// 프로젝트 진행 중 멤버 (작성자는 미포함)
@Getter
public class ProjectMemberResponse {
    private final String nickname;
    private final Integer department;

    public ProjectMemberResponse(Member member, Integer department) {
        this.nickname = member.getNickname();
        this.department = department;
    }

}
