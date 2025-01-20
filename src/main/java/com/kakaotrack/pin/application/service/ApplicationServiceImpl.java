package com.kakaotrack.pin.application.service;

import com.kakaotrack.pin.application.dto.AddApplicationRequest;
import com.kakaotrack.pin.application.repository.ApplicationRepository;
import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import com.kakaotrack.pin.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

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
}
