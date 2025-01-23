package com.kakaotrack.pin.project.dto;

import com.kakaotrack.pin.domain.Field;
import com.kakaotrack.pin.domain.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 생성 시 분야 추가를 위한
@Getter
@NoArgsConstructor
public class AddFieldRequest {

    private Integer department;
    private Integer range;
    private Project projectId;

    // Field 빌드
    public Field toEntity() {
        return Field.builder()
                .project(projectId)   // fk
                .department(department)
                .range(range)
                .build();
    }
}
