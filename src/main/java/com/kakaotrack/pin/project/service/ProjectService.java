package com.kakaotrack.pin.project.service;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.project.dto.AddFieldRequest;
import com.kakaotrack.pin.project.dto.AddProjectRequest;
import com.kakaotrack.pin.project.dto.ProjectResponse;
import com.kakaotrack.pin.project.repository.FieldRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // final이나 null 을 위해
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FieldRepository fieldRepository;

    // 프로젝트 저장
    public Project save(AddProjectRequest request) {
        return projectRepository.save(request.toEntity());
    }

    // field 저장
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
    public List<ProjectResponse> findAll() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(ProjectResponse::new)  // project -> ProjectResponse 변환
                .collect(Collectors.toList());
    }
}
