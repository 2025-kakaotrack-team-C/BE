package com.kakaotrack.pin.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ReviewRequestDto {
    private Long projectId;
    private Long reviewerId;  // 평가하는 사람 ID 추가
    private Long revieweeId;  // 평가 대상자 ID
    private Integer rating;   // 1~5 점수

    @Builder
    public ReviewRequestDto(Long projectId, Long revieweeId, Integer rating) {
        this.projectId = projectId;
        this.reviewerId = revieweeId; // modify
        this.revieweeId = revieweeId;
        this.rating = rating;
    }
}
