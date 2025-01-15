package com.kakaotrack.pin.project.controller;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.project.dto.AddProjectRequest;
import com.kakaotrack.pin.project.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {

    private final ProjectService projectService;

    // 새 프로젝트 생성(create) + Field 테이블 값 저장 -> Transactional 사용
    @PostMapping("api/projects")
    @Transactional
    public ResponseEntity<Project> addProject(@RequestBody AddProjectRequest request) {
        // projectService를 사용해서 새로운 프로젝트 저장
        Project saveProject = projectService.save(request);

        // Field 저장
        List<Field> fields = projectService.fieldSave(saveProject, request.getFields());

        // 프로젝트에 저장된 필드를 추가해서 반환
        saveProject.getFields().addAll(fields);

        // 생성된 프로젝트 엔티티 + http 201(created) 상태 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProject);

    }
}
