package com.kakaotrack.pin.application.repository;

import com.kakaotrack.pin.domain.Application;
import com.kakaotrack.pin.jwt.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByAppMember(Member member);
}
