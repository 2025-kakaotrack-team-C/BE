package com.kakaotrack.pin.mypage.controller;

import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.mypage.dto.MyPageRequestDTO;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;
import com.kakaotrack.pin.mypage.service.MyPageService;
import com.kakaotrack.pin.jwt.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    private final MemberRepository memberRepository;  // 추가

    @GetMapping
    public ResponseEntity<MyPageResponseDTO> getMyPageInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        MyPageResponseDTO response = myPageService.getMyPageInfo(member.getId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<MyPageResponseDTO> updateMyPageInfo(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MyPageRequestDTO requestDTO) {
        Member member = memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        MyPageResponseDTO response = myPageService.updateMyPageInfo(member.getId(), requestDTO);
        return ResponseEntity.ok(response);
    }
}