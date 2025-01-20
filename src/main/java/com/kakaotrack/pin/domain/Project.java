package com.kakaotrack.pin.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kakaotrack.pin.jwt.member.Member;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "project")
public class Project {

    // TODO 유효성 검사 추가
    // TODO 디테일 추가하기 (다른 엔티티도)
    // TODO member 연결 (무한 참조 설정)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 증가
    @Column(name = "project_id", updatable = false) // 수정 불가
    private Long projectId;

    // 유저 entity 생성 전 임시 데이터
    // 유저 entity 생성 시 fk - 다대일
//    @Column(name = "user_id", columnDefinition = "integer default 1")
//    private Long userId;

    // Member와 연결 (FK)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)  // 탈퇴 유저를 위해 nullable = true
    private Member member;

    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "difficult")
    private Integer difficult;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "status", nullable = false, columnDefinition = "integer default 0")
    private Integer status = 0;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 엔티티 참조

    // Field 엔티티 (일대다)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)    // private Project project의 project와 연결된다는 의미
    @JsonManagedReference // 무한 참조 방지
    private List<Field> fields = new ArrayList<>();     // project 하나 당 field가 여러개일 수 있으므로 list로 설정

    @Builder
    public Project(Member member, String title, String description, Integer difficult, LocalDate deadline, Integer status) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
        this.status = (status != null) ? status : 0;   // 기본값 설정
    }

    public void setMember(Member member) {
        this.member = member;
    }

    // 수정할 때 update 메서드 추가
    public void update(String title, String description, Integer difficult, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.difficult = difficult;
        this.deadline = deadline;
    }
}
