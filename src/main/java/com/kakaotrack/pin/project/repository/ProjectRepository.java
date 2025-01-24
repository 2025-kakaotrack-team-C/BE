package com.kakaotrack.pin.project.repository;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Project jpa
public interface ProjectRepository extends JpaRepository <Project, Long>{

    List<Project> findByMember(Member member);
    List<Project> findByMemberAndStatus(Member member, Integer status);
    Project findByProjectId(Long projectId);
    List<Project> findByStatus(Integer status);
}
