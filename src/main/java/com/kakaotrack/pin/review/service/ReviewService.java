package com.kakaotrack.pin.review.service;

import com.kakaotrack.pin.review.dto.ReviewRequestDto;
import com.kakaotrack.pin.review.dto.ReviewResponseDto;
import com.kakaotrack.pin.review.entity.Project_Member;
import com.kakaotrack.pin.review.entity.Review;
import com.kakaotrack.pin.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final Project_MemberRepository project_memberRepository;

    // 1. 리뷰 생성
    @Transactional  // 데이터를 변경하는 작업이므로 읽기 전용 해제
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long userId) {
        // 1-1. 리뷰어(로그인한 사용자)가 해당 프로젝트 멤버인지 확인
        Project_Member reviewerMember = project_memberRepository.findByProjectIdAndUserId(requestDto.getProjectId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트의 멤버가 아닙니다."));

        // 1-2. 리뷰 대상자가 해당 프로젝트 멤버인지 확인
        Project_Member revieweeMember = project_memberRepository.findByProjectIdAndUserId(requestDto.getProjectId(), requestDto.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("평가 대상자가 해당 프로젝트의 멤버가 아닙니다."));

        // 1-3. 자기 자신 평가 방지
        if(userId.equals(requestDto.getRevieweeId())) {
            throw new IllegalArgumentException("자기 자신을 평가할 수 없습니다.");
        }

        // 1-4. 리뷰 생성
        Review review = Review.builder()
                .project(reviewerMember.getProject())
                .reviewer(reviewerMember.getUser())
                .reviewee(revieweeMember.getUser())
                .rating(requestDto.getRating())
                .build();

        // 1-5. 저장하고 DTO로 변환해서 반환
        return ReviewResponseDto.from(reviewRepository.save(review));
    }

    // 2. 내가 받은 리뷰 목록 조회
    public List<ReviewResponseDto> getMyReceivedReviews(Long userId) {
        List<Review> reviews = reviewRepository.findAllByRevieweeId(userId);
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }

    // 3. 특정 프로젝트에서 받은 리뷰 조회
    public List<ReviewResponseDto> getProjectReviews(Long projectId, Long userId) {
        List<Review> reviews = reviewRepository.findAllByProjectIdAndRevieweeId(projectId, userId);
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }
}