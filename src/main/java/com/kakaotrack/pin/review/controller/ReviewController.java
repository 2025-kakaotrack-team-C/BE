package com.kakaotrack.pin.review.controller;

import com.kakaotrack.pin.review.dto.ReviewRequestDto;
import com.kakaotrack.pin.review.dto.ReviewResponseDto;
import com.kakaotrack.pin.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 1. 리뷰 생성 (팀원 평가, 1-5점)
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto) {
        return ResponseEntity.ok(reviewService.createReview(requestDto));
    }

    // 2. 내가 받은 모든 리뷰 조회 (누적 평점용)
    @GetMapping("/received")
    public ResponseEntity<List<ReviewResponseDto>> getMyReceivedReviews() {
        return ResponseEntity.ok(reviewService.getMyReceivedReviews());
    }

    // 3. 프로젝트별 받은 리뷰 조회
    @GetMapping("/projects/{projectId}/received")
    public ResponseEntity<List<ReviewResponseDto>> getProjectReviews(@PathVariable Long projectId) {
        return ResponseEntity.ok(reviewService.getProjectReviews(projectId));
    }
}
