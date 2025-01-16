package com.kakaotrack.pin.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyPageRequestDTO {
    private String major;
    private String github;
    private List<Integer> languages;     // 프론트에서 받을 언어 ID 리스트
    private List<Integer> departments;    // 프론트에서 받을 분야 ID 리스트
}
