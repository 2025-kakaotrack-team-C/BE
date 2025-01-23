package com.kakaotrack.pin.project.service;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.project.dto.*;
import com.kakaotrack.pin.project.repository.FieldRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ProjectService {

    // 프로젝트 저장
    public Project save(AddProjectRequest request, String username);

    // field 저장
    public List<Field> fieldSave(Project project, List<AddFieldRequest> requests);

    // 프로젝트 전체 조회
    public List<AllProjectResponse> findAll();

    // 프로젝트 세부 조회
    public ProjectViewResponse getProjectDetails (long id);


    // 프로젝트 수정
    // TODO: 수정할 때 분야 추가하면 오류
    @Transactional
    public Project updateProject(Long projectId, UpdateProjectRequest request);

    // 프로젝트 진행중
    public IngProjectResponse ingProject (long id);

}