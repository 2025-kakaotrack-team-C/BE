package com.kakaotrack.pin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    // FE 연동 관련 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // 프론트엔드 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // OPTIONS 추가
                .allowedHeaders("*")
                .allowCredentials(true)  // 인증 정보 허용
                .maxAge(3600);  // preflight 캐시 시간
    }
}
