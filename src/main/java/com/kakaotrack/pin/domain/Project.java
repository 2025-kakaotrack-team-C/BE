package com.kakaotrack.pin.domain;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가
    @Column(name = "project_id", updatable = false) // 수정 불가
    private Long projectId;

    // 유저 entity 생성 전 임시 데이터
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "difficult")
    private Integer difficult;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "status")
    private Integer status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Project(Long userId, String title, String description, Integer difficult, LocalDate deadline, Integer status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
        this.status = status;
    }
}
