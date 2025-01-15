package com.kakaotrack.pin.project.repository;

import com.kakaotrack.pin.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository <Project, Long>{
}
