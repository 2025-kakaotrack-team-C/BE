package com.kakaotrack.pin.project.service;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.project.dto.addProjectRequest;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    // 프로젝트 저장
    public Project save(addProjectRequest request) {
        return projectRepository.save(request.toEntity());
    }
}
