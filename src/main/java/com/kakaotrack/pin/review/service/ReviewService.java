package com.kakaotrack.pin.review.service;

import com.kakaotrack.pin.application.repository.ProjectMemberRepository;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.mypage.entity.Language;
import com.kakaotrack.pin.mypage.repository.LanguageRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import com.kakaotrack.pin.review.dto.MemberResponseDto;
import com.kakaotrack.pin.review.dto.ReviewRequestDto;
import com.kakaotrack.pin.review.dto.ReviewResponseDto;
import com.kakaotrack.pin.review.entity.Project_Member;
import com.kakaotrack.pin.review.entity.Review;
import com.kakaotrack.pin.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ProjectRepository projectRepository;  // 추가

    // 리뷰 생성
    // 리뷰 생성
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long userId) {
        // 프로젝트 조회
        Project project = projectRepository.findById(requestDto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));

        // 1. 리뷰어가 프로젝트 작성자이거나 프로젝트 멤버인지 확인
        boolean isProjectCreator = project.getMember().getId().equals(userId);

        if (!isProjectCreator) {
            // 작성자가 아닌 경우 프로젝트 멤버인지 확인
            project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트의 멤버가 아닙니다."));
        }

        // 2. 리뷰 대상자가 프로젝트 작성자이거나 프로젝트 멤버인지 확인
        Member reviewee;
        if (project.getMember().getId().equals(requestDto.getRevieweeId())) {
            reviewee = project.getMember(); // 평가 대상자가 프로젝트 작성자인 경우
        } else {
            // 평가 대상자가 프로젝트 멤버인지 확인
            Project_Member revieweeMember = project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), requestDto.getRevieweeId())
                    .orElseThrow(() -> new IllegalArgumentException("평가 대상자가 해당 프로젝트의 멤버가 아닙니다."));
            reviewee = revieweeMember.getMember();
        }

        // 3. 자기 자신 평가 방지
        if(userId.equals(requestDto.getRevieweeId())) {
            throw new IllegalArgumentException("자기 자신을 평가할 수 없습니다.");
        }

        // 4. 리뷰 생성 및 저장
        Member reviewer = isProjectCreator ? project.getMember() :
                project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), userId).get().getMember();

        Review review = Review.builder()
                .project(project)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .rating(requestDto.getRating())
                .build();

        Review savedReview = reviewRepository.save(review);

        // 프로젝트의 전체 멤버 수 계산 (프로젝트 생성자 + 프로젝트 멤버)
        long totalMembers = 1 + project_memberRepository.countByProject_ProjectId(project.getProjectId());

        // 현재까지 작성된 리뷰 수
        long completedReviews = reviewRepository.countByProject_ProjectId(project.getProjectId());

        // 예상되는 총 리뷰 수 (n명이 각각 n-1명을 리뷰)
        long expectedTotalReviews = totalMembers * (totalMembers - 1);

        // 모든 멤버의 리뷰가 완료되었을 때만 status를 4로 변경
        if (completedReviews >= expectedTotalReviews) {
            project.setStatus(4);
            projectRepository.save(project);
        }

        return ReviewResponseDto.from(savedReview);
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

    public List<MemberResponseDto> getProjectMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다."));

        // 작성자 정보 생성
        List<Language> leaderLanguages = languageRepository.findByMember_Id(project.getMember().getId());
        // 여기서 작성자의 rating 조회
        Integer leaderRating = (int) Math.round(reviewRepository.findByRevieweeId(project.getMember().getId())
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0));

        MemberResponseDto leaderDto = MemberResponseDto.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getTitle())
                .userId(project.getMember().getId())
                .nickname(project.getMember().getNickname())
                .language(leaderLanguages.isEmpty() ? 0 : leaderLanguages.get(0).getLanguage())
                .department(0)
                .rating(leaderRating)  // rating 추가
                .build();

        // 팀원 정보 조회
        List<Project_Member> projectMembers = projectMemberRepository.findByProjectProjectId(projectId);
        List<MemberResponseDto> memberDtos = projectMembers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        List<MemberResponseDto> allMembers = new ArrayList<>();
        allMembers.add(leaderDto);
        allMembers.addAll(memberDtos);

        return allMembers;
    }

    private MemberResponseDto convertToDto(Project_Member pm) {
        List<Language> userLanguages = languageRepository.findByMember_Id(pm.getMember().getId());

        // 팀원의 rating 평균 계산 추가
        Integer memberRating = (int) Math.round(reviewRepository.findByRevieweeId(pm.getMember().getId())
                .stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0));

        return MemberResponseDto.builder()
                .projectId(pm.getProject().getProjectId())
                .projectTitle(pm.getProject().getTitle())
                .nickname(pm.getMember().getNickname())
                .userId(pm.getMember().getId())
                .language(userLanguages.isEmpty() ? 0 : userLanguages.get(0).getLanguage())
                .department(pm.getDepartment())
                .rating(memberRating)  // rating 추가
                .build();
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