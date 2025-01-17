package com.kakaotrack.pin.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
public class MyPageResponseDTO {
    private String username;
    private String major;
    private String github;
    private List<Integer> language;     // 사용자가 선택한 언어 ID 리스트
    private List<Integer> department;    // 사용자가 선택한 분야 ID 리스트
}