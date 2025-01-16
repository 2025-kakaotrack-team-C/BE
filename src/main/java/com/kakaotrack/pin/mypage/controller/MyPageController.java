package com.kakaotrack.pin.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    // 여기에 필요할 메서드:
    // 1. 현재 유저의 모든 정보 조회 (GET)
    // 2. 현재 유저의 정보 수정 (PUT)
}
