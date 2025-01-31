package com.kakaotrack.pin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kakaotrack.pin.jwt.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", updatable = false)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "member-application")
    private Member appMember;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference(value = "project-application")
    private Project appProject;

    @Column(name = "department")
    private Integer department;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Integer status;

    @Builder
    public Application(Member appMember, Project appProject, Integer department, Integer status) {
        this.appMember = appMember;
        this.appProject = appProject;
        this.department = department;
        this.status = (status != null) ? status : 1;
    }

    // status 값 변경
    public void setStatus(Integer status) {
        this.status = status;
    }
}
