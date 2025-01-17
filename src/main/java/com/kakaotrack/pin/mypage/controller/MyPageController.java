package com.kakaotrack.pin.mypage.controller;

import com.kakaotrack.pin.mypage.dto.MyPageRequestDTO;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;
import com.kakaotrack.pin.mypage.service.MyPageService;
import com.kakaotrack.pin.jwt.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<MyPageResponseDTO> getMyPageInfo(@AuthenticationPrincipal Member member) {
        MyPageResponseDTO response = myPageService.getMyPageInfo(member.getId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping 
    public ResponseEntity<MyPageResponseDTO> updateMyPageInfo(
            @AuthenticationPrincipal Member member,
            @RequestBody MyPageRequestDTO requestDTO) {
        MyPageResponseDTO response = myPageService.updateMyPageInfo(member.getId(), requestDTO);
        return ResponseEntity.ok(response);
    }
}