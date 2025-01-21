package com.kakaotrack.pin.application.service;

import com.kakaotrack.pin.application.dto.AddApplicationRequest;
import com.kakaotrack.pin.application.dto.ApplicationResponse;
import com.kakaotrack.pin.application.repository.ApplicationRepository;
import com.kakaotrack.pin.application.repository.ProjectMemberRepository;
import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import com.kakaotrack.pin.review.entity.Project_Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // 지원서 저장
    public Application save(AddApplicationRequest request, String username, Long projectId) {

        // 멤버 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("project not found: " + projectId));

        // Application 엔티티 생성
        Application application = request.toEntity(member, project);

        return applicationRepository.save(application);

    }

    // 지원서 조회
    public List<ApplicationResponse> findAll(){
        List<Application> applications = applicationRepository.findAll();

        return applications.stream()
                .map(ApplicationResponse::new)  // ApplicationResponse로 변환
                .collect(Collectors.toList());  // 리스트로
    }

    // 지원서 수락
    // TODO 수락 시 멤버 테이블 저장
    public void acceptApplication(Long applicationId, Integer status){
        // 지원서 조회
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        // 지원서 상태 변경 (2 = 수락)
        application.setStatus(status);

        // DB 저장
        applicationRepository.save(application);

        // 프로젝트 멤버 테이블 추가
        addProjectMember(application.getAppProject(), application.getAppMember(), application.getDepartment());
    }

    // 프로젝트 멤버 테이블 추가 메서드
    public void addProjectMember(Project project, Member member, Integer department) {
        Project_Member projectMember = new Project_Member();

        projectMember.setProject(project);
        projectMember.setMember(member); //modify setUser --> setMember
        projectMember.setDepartment(department);

        projectMemberRepository.save(projectMember);
    }

    // 지원서 거절
    public void rejectApplication(Long applicationId, Integer status) {
        // 지원서 조회
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        // 지원서 상태 변경 (3 = 거절)
        application.setStatus(status);

        applicationRepository.save(application);
    }

}
