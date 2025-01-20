package com.kakaotrack.pin.project.controller;
//
import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.project.dto.*;
import com.kakaotrack.pin.project.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {

    private final ProjectService projectService;

    // 새 프로젝트 생성(create) + Field 테이블 값 저장 -> Transactional 사용
    @PostMapping("api/projects")
    @Transactional
    public ResponseEntity<Project> addProject(@RequestBody AddProjectRequest request) {
        // 로그인 한 유저 정보 가져오기 ( username으로 가져옴 )
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // projectService를 사용해서 새로운 프로젝트 저장 및 필드 연결
        Project saveProject = projectService.save(request, username);

        // Field 저장
        List<Field> fields = projectService.fieldSave(saveProject, request.getFields());

        // 프로젝트에 저장된 필드를 추가해서 반환
        saveProject.getFields().addAll(fields);

        // 생성된 프로젝트 엔티티 + http 201(created) 상태 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProject);
    }

    // 프로젝트 전체 조회
    @GetMapping("api/projects")
    public List<ProjectResponse> findAllProjects() {
        return projectService.findAll();
    }

    // 프로젝트 세부 조회
    @GetMapping("api/projects/{id}")
    public ProjectViewResponse getProjectDetails(@PathVariable long id) {
        return projectService.getProjectDetails(id);
    }

    // 프로젝트 수정(필드 수정 포함)
    @PutMapping("/api/projects/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @RequestBody UpdateProjectRequest request) {

        return projectService.updateProject(id, request);
    }

}
