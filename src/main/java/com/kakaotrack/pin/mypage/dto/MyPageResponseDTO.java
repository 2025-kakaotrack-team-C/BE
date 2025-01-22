package com.kakaotrack.pin.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
public class MyPageResponseDTO {
    // 기본 사용자 정보
    private Long id;
    private String nickname;
    private String username;
    private String major;
    private String github;
    private List<Integer> language;     // 사용자가 선택한 언어 ID 리스트
    private List<Integer> department;    // 사용자가 선택한 분야 ID 리스트

    // 프로젝트 관련 정보 리스트
    private List<ProjectStatusInfo> writtenProjects;
    private List<ApplicationStatusInfo> appliedProjects;
    private List<ProjectStatusInfo> completedProjects;
    private List<ProjectStatusInfo> ongoingProjects;

    @Getter
    @Setter
    public static class ProjectStatusInfo {
        private Long projectId;
        private String title;
        private Integer difficult;
        private LocalDate deadline;
        private Integer status;
    }

    @Getter
    @Setter
    public static class ApplicationStatusInfo {
        private Long projectId;
        private String title;
        private Integer difficult;
        private LocalDate deadline;
        private Integer status;
        private Integer department;  // 지원 분야
    }
}