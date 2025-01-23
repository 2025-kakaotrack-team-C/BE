package com.kakaotrack.pin.review.dto;

import com.kakaotrack.pin.mypage.entity.Language;
import com.kakaotrack.pin.review.entity.Project_Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long projectId;
    private String projectTitle;
    private String nickname;
    private Integer language;
    private Integer department;

    @Builder  // @Builder 어노테이션 추가
    public MemberResponseDto(Long projectId, String projectTitle,
                             String nickname, Integer language, Integer department) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.nickname = nickname;
        this.language = language;
        this.department = department;
    }
}