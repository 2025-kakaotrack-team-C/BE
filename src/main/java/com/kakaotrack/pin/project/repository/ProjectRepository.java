package com.kakaotrack.pin.project.repository;

import com.kakaotrack.pin.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

// Project jpa
public interface ProjectRepository extends JpaRepository <Project, Long>{
}
