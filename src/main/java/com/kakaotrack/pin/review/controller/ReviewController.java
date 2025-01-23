package com.kakaotrack.pin.review.controller;

import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;
import com.kakaotrack.pin.mypage.service.MyPageService;
import com.kakaotrack.pin.review.dto.MemberResponseDto;
import com.kakaotrack.pin.review.dto.ReviewRequestDto;
import com.kakaotrack.pin.review.dto.ReviewResponseDto;
import com.kakaotrack.pin.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final MyPageService myPageService;
    private final MemberRepository memberRepository;

    // 1. 리뷰 작성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto) {
        log.info("컨트롤러 호출됨!");
        log.info("RequestDto 내용: {}", requestDto.toString());
        return ResponseEntity.ok(reviewService.createReview(requestDto, requestDto.getReviewerId()));
    }

    // 내가 받은 모든 리뷰 조회
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getMyReceivedReviews(@RequestParam Long userId) {  // userId는 토큰에서 가져오기
        List<ReviewResponseDto> reviews = reviewService.getMyReceivedReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/project/member/{projectId}")
    public ResponseEntity<List<MemberResponseDto>> getProjectMembers(@PathVariable Long projectId)
    {
        log.info("프로젝트 ID: {}", projectId);  // projectId가 제대로 들어오는지
        List<MemberResponseDto> members = reviewService.getProjectMembers(projectId);
        log.info("조회된 멤버 수: {}", members.size());  // 멤버가 조회되는지
        return ResponseEntity.ok(members);
    }

    // 프로젝트별 받은 리뷰 조회
    @GetMapping("/project/{id}")
    public ResponseEntity<List<ReviewResponseDto>> getProjectReviews(
            @PathVariable Long id,
            @RequestBody ReviewResponseDto responseDto) {
        return ResponseEntity.ok(reviewService.getProjectReviews(id, responseDto));
    }


}
