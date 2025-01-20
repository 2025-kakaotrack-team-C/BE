package com.kakaotrack.pin.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFieldRequest {

    private Long fieldId;
    private Integer department;
    private Integer range;


}
