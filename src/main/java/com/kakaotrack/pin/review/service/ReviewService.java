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

        // 2. 리뷰 대상자가 해당 프로젝트 멤버인지 확인
        Project_Member revieweeMember = project_memberRepository.findByProject_ProjectIdAndMember_Id(requestDto.getProjectId(), requestDto.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("평가 대상자가 해당 프로젝트의 멤버가 아닙니다."));

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

    public List<MemberResponseDto> getProjectMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 없습니다."));

        // 작성자 정보 생성
        List<Language> leaderLanguages = languageRepository.findByMember_Id(project.getMember().getId());
        MemberResponseDto leaderDto = MemberResponseDto.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getTitle())
                .userId(project.getMember().getId())    // 추가
                .nickname(project.getMember().getNickname())
                .language(leaderLanguages.isEmpty() ? 0 : leaderLanguages.get(0).getLanguage())
                .department(0)
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
        return MemberResponseDto.builder()
                .projectId(pm.getProject().getProjectId())
                .projectTitle(pm.getProject().getTitle())
                .nickname(pm.getMember().getNickname())
                .userId(pm.getMember().getId())     // 추가
                .language(userLanguages.isEmpty() ? 0 : userLanguages.get(0).getLanguage())
                .department(pm.getDepartment())
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