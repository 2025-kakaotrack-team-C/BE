package com.kakaotrack.pin.application.service;

import com.kakaotrack.pin.application.dto.AddApplicationRequest;
import com.kakaotrack.pin.application.dto.ApplicationResponse;
import com.kakaotrack.pin.domain.Application;

import java.util.List;


public interface ApplicationService {

    // 지원서 저장
    public Application save(AddApplicationRequest request, String username, Long projectId);

    // 지원서 조회
    public List<ApplicationResponse> findAll();

    // 지원서 수락
    public void acceptApplication(Long applicationId, Integer status);
}
