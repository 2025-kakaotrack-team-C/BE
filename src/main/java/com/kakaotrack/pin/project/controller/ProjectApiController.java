package com.kakaotrack.pin.project.controller;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.project.dto.addProjectRequest;
import com.kakaotrack.pin.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {

    private final ProjectService projectService;

    // 프로젝트 생성(create)
    @PostMapping("api/projects")
    public ResponseEntity<Project> addProject(@RequestBody addProjectRequest request) {
        // projectService를 사용해서 새로운 프로젝트 저장
        Project saveProject = projectService.save(request);

        // 생성된 프로젝트 엔티티 + http 201(created) 상태 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProject);

    }
}
