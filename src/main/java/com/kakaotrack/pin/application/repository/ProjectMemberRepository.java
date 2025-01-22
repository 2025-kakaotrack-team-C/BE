package com.kakaotrack.pin.application.repository;

import com.kakaotrack.pin.review.entity.Project_Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<Project_Member, Long> {
    Optional<Project_Member> findByProject_ProjectIdAndMember_Id(Long projectId, Long memberId);


}
