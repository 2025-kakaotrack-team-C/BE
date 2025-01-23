package com.kakaotrack.pin.project.service;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.project.dto.*;
import com.kakaotrack.pin.project.repository.FieldRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import com.kakaotrack.pin.review.entity.Project_Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // final이나 null 을 위해
@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final FieldRepository fieldRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 저장
    @Override
    public Project save(AddProjectRequest request, String username) {

        // 사용자 조회
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Project 엔티티 생성 ( Member을 Project에 설정)
        Project project = request.toEntity();
        project.setMember(member);

        return projectRepository.save(project);
    }

    // field 저장
    @Override
    public List<Field> fieldSave(Project project, List<AddFieldRequest> requests) {

        List<Field> savedFields = new ArrayList<>();    // 객체를 모아둘 리스트 초기화

        // 리스트 반복문
        for (AddFieldRequest dto : requests) {
            Field field = dto.toEntity();
            field.setProject(project);
            savedFields.add(field);
        }

        return fieldRepository.saveAll(savedFields);
    }

    // 프로젝트 전체 조회
    @Override
    public List<AllProjectResponse> findAll() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(AllProjectResponse::new)  // project -> AllProjectResponse 변환
                .collect(Collectors.toList());
    }

    // 프로젝트 세부 조회
    @Override
    public ProjectViewResponse getProjectDetails (long id){
        // 프로젝트 조회
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));

        // 작성자 정보 생성
        var userResponse = new UserResponse(
                project.getMember().getId(),
                project.getMember().getUsername(),
                project.getMember().getNickname()
        );

        // 분야(필드) 정보 생성
        var fieldResponse = project.getFields().stream()
                .map(field -> new FieldResponse(field.getFieldId(), field.getDepartment(), field.getRange()))
                .collect(Collectors.toList());

        return new ProjectViewResponse(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getDifficult(),
                project.getDeadline(),
                project.getStatus(),
                project.getCreatedAt(),
                fieldResponse,
                userResponse
        );
    }

    // 프로젝트 수정
    // TODO: 수정할 때 분야 추가하면 오류
    @Override
    @Transactional
    public Project updateProject(Long projectId, UpdateProjectRequest request) {
        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));

        // 프로젝트 정보 수정
        project.update(request.getTitle(), request.getDescription(), request.getDifficult(), request.getDeadline());

        // 필드 정보 수정 메서드로 전송 (바로 아래 메서드 있음)
        updateFields(project, request.getFields());

        return project;
    }

    // field 수정 메서드
    private void updateFields(Project project, List<UpdateFieldRequest> fieldRequests) {
        for (UpdateFieldRequest fieldRequest : fieldRequests) {
            Field field = fieldRepository.findById(fieldRequest.getFieldId())
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + fieldRequest.getFieldId()));

            field.update(fieldRequest.getDepartment(), fieldRequest.getRange());
        }
    }

    // 프로젝트 진행중
    public IngProjectResponse ingProject (long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("projectId not found: " +  id));

        // 프로젝트 리스폰으로 변경
        ProjectResponse projectResponse = new ProjectResponse(project);

        // TODO 팀 닉ㄱ네임 추가
        // 프로젝트 멤버 정보 생성
        var projectMemberResponse = project.getProjectMembers().stream()
                .map(projectMember -> new ProjectMemberResponse(projectMember.getMember(), projectMember.getDepartment()))
                .collect(Collectors.toList());

        return new IngProjectResponse(projectResponse, projectMemberResponse);
    }

    // 프로젝트 마감
    public void finishProject (long id){
        // 프로젝트 조회
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));

        // 프로젝트 상태 변경 (3 = 프로젝트 마감)
        project.setStatus(3);

        projectRepository.save(project);
    }
}
