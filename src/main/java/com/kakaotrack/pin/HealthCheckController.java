package com.kakaotrack.pin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// healthcheck 컨트롤러 (/health)
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "서버 실행중";
    }
}
