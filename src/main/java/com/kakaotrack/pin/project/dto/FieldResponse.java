package com.kakaotrack.pin.project.dto;

import lombok.Getter;

// 프로젝트 세부 조회 때 분야 응답
@Getter
public class FieldResponse {
    private final long fieldId;
    private final int department;
    private final int range;

    public FieldResponse(long fieldId, int department, int range) {
        this.fieldId = fieldId;
        this.department = department;
        this.range = range;
    }
}
