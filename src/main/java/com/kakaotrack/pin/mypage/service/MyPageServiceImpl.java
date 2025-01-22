package com.kakaotrack.pin.mypage.service;

import com.kakaotrack.pin.application.repository.ApplicationRepository;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.mypage.dto.MyPageRequestDTO;
import com.kakaotrack.pin.mypage.dto.MyPageResponseDTO;
import com.kakaotrack.pin.mypage.repository.LanguageRepository;
import com.kakaotrack.pin.mypage.repository.UserDepartmentRepository;
import com.kakaotrack.pin.mypage.entity.Language;          // 추가
import com.kakaotrack.pin.mypage.entity.UserDepartment;    // 추가
import com.kakaotrack.pin.project.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;         // 추가
    private final ApplicationRepository applicationRepository; // 추가


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

        // project information
        // 작성한 프로젝트 조회
        List<MyPageResponseDTO.ProjectStatusInfo> writtenProjects = projectRepository.findByMember(member)
                .stream()
                .map(project -> {
                    MyPageResponseDTO.ProjectStatusInfo info = new MyPageResponseDTO.ProjectStatusInfo();
                    info.setProjectId(project.getProjectId());
                    info.setTitle(project.getTitle());
                    info.setDifficult(project.getDifficult());
                    info.setDeadline(project.getDeadline());
                    info.setStatus(project.getStatus());
                    return info;
                })
                .collect(Collectors.toList());

        // 지원한 프로젝트 조회
        List<MyPageResponseDTO.ApplicationStatusInfo> appliedProjects = applicationRepository.findByAppMember(member)
                .stream()
                .map(application -> {
                    MyPageResponseDTO.ApplicationStatusInfo info = new MyPageResponseDTO.ApplicationStatusInfo();
                    Project project = application.getAppProject();
                    info.setProjectId(project.getProjectId());
                    info.setTitle(project.getTitle());
                    info.setDifficult(project.getDifficult());
                    info.setDeadline(project.getDeadline());
                    info.setStatus(project.getStatus());
                    info.setDepartment(application.getDepartment());
                    return info;
                })
                .collect(Collectors.toList());

        // 완료된 프로젝트 조회 (status = 4)
        List<MyPageResponseDTO.ProjectStatusInfo> completedProjects = projectRepository.findByMemberAndStatus(member, 3)
                .stream()
                .map(project -> {
                    MyPageResponseDTO.ProjectStatusInfo info = new MyPageResponseDTO.ProjectStatusInfo();
                    info.setProjectId(project.getProjectId());
                    info.setTitle(project.getTitle());
                    info.setDifficult(project.getDifficult());
                    info.setDeadline(project.getDeadline());
                    info.setStatus(project.getStatus());
                    return info;
                })
                .collect(Collectors.toList());

        // 진행중인 프로젝트 조회 (status = 3)
        List<MyPageResponseDTO.ProjectStatusInfo> ongoingProjects = projectRepository.findByMemberAndStatus(member, 2)
                .stream()
                .map(project -> {
                    MyPageResponseDTO.ProjectStatusInfo info = new MyPageResponseDTO.ProjectStatusInfo();
                    info.setProjectId(project.getProjectId());
                    info.setTitle(project.getTitle());
                    info.setDifficult(project.getDifficult());
                    info.setDeadline(project.getDeadline());
                    info.setStatus(project.getStatus());
                    return info;
                })
                .collect(Collectors.toList());

        // DTO에 프로젝트 정보 설정



        // DTO로 변환
        MyPageResponseDTO responseDTO = new MyPageResponseDTO();
        responseDTO.setUsername(member.getUsername());
        responseDTO.setMajor(member.getMajor());
        responseDTO.setGithub(member.getGithub());
        responseDTO.setLanguage(languages);
        responseDTO.setDepartment(departments);
        responseDTO.setNickname(member.getNickname());
        responseDTO.setId(member.getId());
        responseDTO.setWrittenProjects(writtenProjects);
        responseDTO.setAppliedProjects(appliedProjects);
        responseDTO.setCompletedProjects(completedProjects);
        responseDTO.setOngoingProjects(ongoingProjects);

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
