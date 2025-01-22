package com.kakaotrack.pin.application.service;

import com.kakaotrack.pin.application.dto.AddApplicationRequest;
import com.kakaotrack.pin.application.dto.ApplicationResponse;
import com.kakaotrack.pin.application.repository.ApplicationRepository;
import com.kakaotrack.pin.application.repository.ProjectMemberRepository;
import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.project.repository.FieldRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import com.kakaotrack.pin.review.entity.Project_Member;
import jakarta.transaction.Transactional;
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
    private final FieldRepository fieldRepository;

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

    @Transactional
    // 프로젝트 멤버 테이블 추가 메서드
    public void addProjectMember(Project project, Member member, Integer department) {
        Project_Member projectMember = new Project_Member();

        projectMember.setProject(project);
        projectMember.setMember(member); //modify setUser --> setMember
        projectMember.setDepartment(department);

        projectMemberRepository.save(projectMember);

        // 필드 조회
        List<Field> fields = fieldRepository.findByProject_ProjectId(project.getProjectId());

        fields.forEach(field -> {
            // 수락되면 필드의 현재 수락된 멤버 수 증가
            if (field.getDepartment().equals(projectMember.getDepartment())) {    // 지원한 분야랑 같은 분야 확인
                field.setAcceptMember(field.getAcceptMember() + 1); // 같으면 지원 수락 된 인원 1 증가

                // 모집 인원보다 모집된 인원이 크거나 같으면 분야 모집 상태 2(모집 완료)로 수정
                if (field.getAcceptMember() >= field.getRange()) {
                    field.setDepartmentMemberStatus(2);
                }
            }
        });

        fieldRepository.saveAll(fields);

        // 모집중인 분야가 하나라도 있으면 allFieldsComplete 를 false 로
        boolean allFieldsComplete = fields.stream()
                .allMatch(field -> field.getDepartmentMemberStatus() == 2);

        // 모든 필드가 완료 상태면 프로젝트 상태 변경
        if (allFieldsComplete) {
            project.setStatus(2);   // 프로젝트 진행중으로 수정
        }
        projectRepository.save(project);

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
