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
    private Long userId;          // 추가
    private String nickname;
    private Integer language;
    private Integer department;
    private Integer rating;  // rating 필드 추가

    @Builder
    public MemberResponseDto(Long projectId, String projectTitle,
                             Long userId, String nickname, Integer language, Integer department, Integer rating) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.userId = userId;     // 추가
        this.nickname = nickname;
        this.language = language;
        this.department = department;
        this.rating =rating;
    }
}