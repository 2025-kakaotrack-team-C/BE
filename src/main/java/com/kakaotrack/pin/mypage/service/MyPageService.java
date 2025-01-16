package com.kakaotrack.pin.mypage.service;

import com.kakaotrack.pin.mypage.dto.MyPageRequestDTO;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;

public interface MyPageService {
    // 현재 사용자의 모든 정보 조회
    MyPageResponseDTO getMyPageInfo(Long memberId);

    // 현재 사용자의 정보 수정
    MyPageResponseDTO updateMyPageInfo(Long memberId, MyPageRequestDTO request);
}
