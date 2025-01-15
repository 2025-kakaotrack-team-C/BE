package com.kakaotrack.pin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 엔티티 객체가 생성되거나 변경되었을때 자동으로 값 등록 가능
@EnableJpaAuditing
@SpringBootApplication
public class PinApplication {
    public static void main(String[] args) {
        SpringApplication.run(PinApplication.class, args);
    }
}
