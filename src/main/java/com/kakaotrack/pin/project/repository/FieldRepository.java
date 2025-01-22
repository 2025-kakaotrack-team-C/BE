package com.kakaotrack.pin.project.repository;

import com.kakaotrack.pin.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByProject_ProjectId(Long projectId);
}
