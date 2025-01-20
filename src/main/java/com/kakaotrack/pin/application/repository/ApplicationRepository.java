package com.kakaotrack.pin.application.repository;

import com.kakaotrack.pin.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
