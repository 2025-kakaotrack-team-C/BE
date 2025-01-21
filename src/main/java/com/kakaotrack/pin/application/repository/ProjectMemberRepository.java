package com.kakaotrack.pin.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<Project_Member, Long> {
    Optional<Project_Member> findByProjectIdAndUserId(Long projectId, Long userId);

}
