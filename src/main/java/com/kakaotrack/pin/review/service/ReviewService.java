package com.kakaotrack.pin.review.service;

import com.kakaotrack.pin.application.repository.ProjectMemberRepository;
import com.kakaotrack.pin.mypage.entity.Language;
import com.kakaotrack.pin.mypage.repository.LanguageRepository;
import com.kakaotrack.pin.review.dto.MemberResponseDto;
import com.kakaotrack.pin.review.dto.ReviewRequestDto;
import com.kakaotrack.pin.review.dto.ReviewResponseDto;
import com.kakaotrack.pin.review.entity.Project_Member;
import com.kakaotrack.pin.review.entity.Review;
import com.kakaotrack.pin.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectMemberRepository project_memberRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final LanguageRepository languageRepository;

    // 리뷰 생성
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long userId) {
        // 1. 리뷰어(로그인한 사용자)가 해당 프로젝트 멤버인지 확인
        log.info("Project ID: {}, User ID: {}", requestDto.getProjectId(), userId); // check

        Project_Member reviewerMember = project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트의 멤버가 아닙니다."));

        // 2. 리뷰 대상자가 해당 프로젝트 멤버인지 확인
        Project_Member revieweeMember = project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), requestDto.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("평가 대상자가 해당 프로젝트의 멤버가 아닙니다."));

        // 3. 자기 자신 평가 방지
        if(userId.equals(requestDto.getRevieweeId())) {
            throw new IllegalArgumentException("자기 자신을 평가할 수 없습니다.");
        }

        // 4. 리뷰 생성 및 저장
        Review review = Review.builder()
                .project(reviewerMember.getProject())
                .reviewer(reviewerMember.getMember())
                .reviewee(revieweeMember.getMember())
                .rating(requestDto.getRating())
                .build();

        return ReviewResponseDto.from(reviewRepository.save(review));
    }

    // 내가 받은 모든 리뷰 조회
    public List<ReviewResponseDto> getMyReceivedReviews(Long userId) {
        // revieweeId가 현재 로그인한 사용자인 리뷰만 조회
        log.info("User ID: {}",userId);
        List<Review> reviews = reviewRepository.findAllByRevieweeId(userId);
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }

    // project member information
    public List<MemberResponseDto> getProjectMembers(Long projectId) {
        List<Project_Member> projectMembers = projectMemberRepository.findByProjectProjectId(projectId);

        return projectMembers.stream()
                .map(pm -> {
                    List<Language> userLanguages = languageRepository.findByMember_Id(pm.getMember().getId());
                    // Language가 없는 경우 기본값 0으로 처리
                    Integer language = userLanguages.isEmpty() ? 0 : userLanguages.get(0).getLanguage();

                    return MemberResponseDto.builder()
                            .projectId(pm.getProject().getProjectId())
                            .projectTitle(pm.getProject().getTitle())
                            .nickname(pm.getMember().getNickname())
                            .language(language)  // 언어가 없으면 0
                            .department(pm.getDepartment())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 프로젝트별 받은 리뷰 조회
    public List<ReviewResponseDto> getProjectReviews(Long projectId, ReviewResponseDto responseDto) {
        List<Review> reviews = reviewRepository.findAllByProject_ProjectIdAndReviewee_Id(
                projectId,
                responseDto.getReviewId()
        );
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }
}