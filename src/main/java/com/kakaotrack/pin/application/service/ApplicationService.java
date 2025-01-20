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


public interface ApplicationService {

    // 지원서 저장
    public Application save(AddApplicationRequest request, String username, Long projectId);
}
