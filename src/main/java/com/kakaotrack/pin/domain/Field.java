package com.kakaotrack.pin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id", updatable = false)
    private Long fieldId;

    // Project 엔티티 (다대일)
    // project_id - FK
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference  // 무한참조 방지
    private Project project;

    @Column(name = "department", nullable = false)
    private Integer department;

    @Column(name = "range_value", nullable = false)
    private Integer range;

    // 현재 모집된 인원 추가
    @Column(name = "accept_member")
    private Integer acceptMember;

    // 모집 상태 추가(1=모집중, 2=모집 완료)
    @Column(name = "department_member_status")
    private Integer departmentMemberStatus;

    @Builder
    public Field(Project project, Integer department, Integer range, Integer acceptMember, Integer departmentMemberStatus) {
        this.project = project;
        this.department = department;
        this.range = range;
        this.acceptMember = (acceptMember != null) ? acceptMember : 0;
        this.departmentMemberStatus = (departmentMemberStatus != null) ? departmentMemberStatus : 0;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setAcceptMember(Integer i) {
        this.acceptMember = i;
    }

    public void setDepartmentMemberStatus(Integer i) {
        this.departmentMemberStatus = i;
    }

    // 수정을 위한
    public void update(Integer department, Integer range) {
        this.department = department;
        this.range = range;
    }
}
