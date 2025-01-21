package com.kakaotrack.pin.application.repository;

import com.kakaotrack.pin.review.entity.Project_Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<Project_Member, Long> {
}
