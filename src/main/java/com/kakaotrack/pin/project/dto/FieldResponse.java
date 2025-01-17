package com.kakaotrack.pin.project.dto;

import lombok.Getter;

@Getter
public class FieldResponse {
    private final int department;
    private final int range;

    public FieldResponse(int department, int range) {
        this.department = department;
        this.range = range;
    }
}
