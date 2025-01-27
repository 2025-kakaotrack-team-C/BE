package com.kakaotrack.pin.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long projectId;
    private Long reviewerId;  // 평가하는 사람 ID 추가
    private Long revieweeId;  // 평가 대상자 ID
    private Integer rating;   // 1~5 점수
    private Integer status;   // 상태 필드 추가

    @Builder
    public ReviewRequestDto(Long projectId, Long reviewerId, Long revieweeId, Integer rating, Integer status) {
        this.projectId = projectId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.rating = rating;
        this.status = status;
    }
}
