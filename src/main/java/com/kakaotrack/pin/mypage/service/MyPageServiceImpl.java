package com.kakaotrack.pin.mypage.service;

import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.mypage.dto.MyPageRequestDTO;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;
import com.kakaotrack.pin.mypage.repository.LanguageRepository;
import com.kakaotrack.pin.mypage.repository.UserDepartmentRepository;
import com.kakaotrack.pin.mypage.entity.Language;          // 추가
import com.kakaotrack.pin.mypage.entity.UserDepartment;    // 추가
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// 필요할 때만 사용하기(여러명의 사용자가 조회를 할 때 필요!
//@Transactional(readOnly = true)
public class MyPageServiceImpl implements MyPageService{
    private final MemberRepository memberRepository;
    private final LanguageRepository languageRepository;
    private final UserDepartmentRepository userDepartmentRepository;

    // 사용자 정보 조회
    public MyPageResponseDTO getMyPageInfo(Long memberId) {
        // 멤버 조회
        Member member = memberRepository.findById(memberId) // 이부분이 문제?
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 언어와 분야 정보 조회
        List<Integer> languages = languageRepository.findByMember(member)
                .stream()
                .map(Language::getLanguage)
                .collect(Collectors.toList());

        List<Integer> departments = userDepartmentRepository.findByMember(member)
                .stream()
                .map(UserDepartment::getDepartment)
                .collect(Collectors.toList());

        // DTO로 변환
        MyPageResponseDTO responseDTO = new MyPageResponseDTO();
        responseDTO.setUsername(member.getUsername());
        responseDTO.setMajor(member.getMajor());
        responseDTO.setGithub(member.getGithub());
        responseDTO.setLanguage(languages);
        responseDTO.setDepartment(departments);

        return responseDTO;
    }

    // 사용자 정보 수정
    @Transactional
    public MyPageResponseDTO updateMyPageInfo(Long memberId, MyPageRequestDTO requestDTO) {
        // 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 기본 정보 업데이트
        member.setMajor(requestDTO.getMajor());
        member.setGithub(requestDTO.getGithub());

        // 기존 언어, 분야 정보 삭제
        languageRepository.deleteByMember(member);
        userDepartmentRepository.deleteByMember(member);

        // 기존 언어, 분야 정보 삭제 및 새로운 정보 저장 (null 체크 추가)
        if (requestDTO.getLanguage() != null) {
            languageRepository.deleteByMember(member);
            requestDTO.getLanguage().forEach(lang -> {
                Language language = new Language();
                language.setMember(member);
                language.setLanguage(lang);
                languageRepository.save(language);
            });
        }

        if (requestDTO.getDepartment() != null) {
            userDepartmentRepository.deleteByMember(member);
            requestDTO.getDepartment().forEach(dept -> {
                UserDepartment department = new UserDepartment();
                department.setMember(member);
                department.setDepartment(dept);
                userDepartmentRepository.save(department);
            });
        }

        return getMyPageInfo(memberId);
    }
}
