package com.kakaotrack.pin.jwt.service;

import com.kakaotrack.pin.jwt.dto.SignUpDto;
import com.kakaotrack.pin.jwt.token.JwtToken;
import com.kakaotrack.pin.jwt.token.JwtTokenProvider;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member signUp(SignUpDto signUpDto) {
        // 중복 username 체크
        if (memberRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }


        // nickname
        if (memberRepository.findByNickname(signUpDto.getNickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        // Member 객체 생성 (비밀번호 암호화)
        Member member = Member.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname()) // add nickname
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        log.info("1. 로그인 시도: username={}", username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
            // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByusername 메서드 실행
            log.info("2. 인증 객체 생성 완료: {}", authenticationToken);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("3. 인증 검증 완료: {}", authentication);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            log.info("4. JWT 토큰 생성 시작");
            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
            log.info("5. JWT 토큰 생성 완료: {}", jwtToken);

            return jwtToken;
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            throw e;
        }
    }
}