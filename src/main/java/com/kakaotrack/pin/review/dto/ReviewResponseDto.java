package com.kakaotrack.pin.review.dto;

import com.kakaotrack.pin.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long projectId;
    private String projectTitle;
    private String reviewerNickname;
    private Integer rating;

    @Builder
    public ReviewResponseDto(Long reviewId, Long projectId, String projectTitle, String reviewerNickname, Integer rating) {
        this.reviewId = reviewId;
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.reviewerNickname = reviewerNickname;
        this.rating = rating;
    }

    // Entity -> DTO 변환 메서드
    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .projectId(review.getProject().getProjectId())
                .projectTitle(review.getProject().getTitle())
                .reviewerNickname(review.getReviewer().getNickname())
                .rating(review.getRating())
                .build();
    }
}
