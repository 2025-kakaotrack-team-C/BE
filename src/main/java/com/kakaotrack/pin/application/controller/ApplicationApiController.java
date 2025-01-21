package com.kakaotrack.pin.application.controller;

import com.kakaotrack.pin.application.dto.AddApplicationRequest;
import com.kakaotrack.pin.application.dto.ApplicationResponse;
import com.kakaotrack.pin.application.service.ApplicationService;
import com.kakaotrack.pin.domain.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationApiController {

    private final ApplicationService applicationService;

    // 지원서 생성
    @PostMapping("api/applications/{id}")
    public ResponseEntity<Application> addApplication(@RequestBody AddApplicationRequest request, @PathVariable long id) {
        // 로그인 된 username 가져옴
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 지원서 저장
        Application saveApplication = applicationService.save(request, username, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveApplication);
    }

    // 지원서 조회
    @GetMapping("api/applications")
    public List<ApplicationResponse> findAll() {
        return applicationService.findAll();
    }

    // 지원서 수락
    @PutMapping("api/applications/{applicationId}/accept")
    public ResponseEntity<Void> acceptApplication(@PathVariable Long applicationId) {
        applicationService.acceptApplication(applicationId, 2);
        return ResponseEntity.ok().build();
    }

    // 지원서 거절
    @PutMapping("api/applications/{applicationId}/reject")
    public ResponseEntity<Void> rejectApplication(@PathVariable Long applicationId) {
        applicationService.rejectApplication(applicationId, 3);
        return ResponseEntity.ok().build();
    }

}
