package com.kakaotrack.pin.jwt.service;

import com.kakaotrack.pin.jwt.dto.SignUpDto;
import com.kakaotrack.pin.jwt.token.JwtToken;
import com.kakaotrack.pin.jwt.member.Member;

public interface MemberService {
    // 로그인 메소드 선언
    JwtToken signIn(String username, String password);
    Member signUp(SignUpDto signUpDto);
}