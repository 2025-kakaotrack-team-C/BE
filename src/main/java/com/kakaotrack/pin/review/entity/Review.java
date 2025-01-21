package com.kakaotrack.pin.review.entity;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;  // PK

    @ManyToOne
    @JoinColumn(name = "project_id")  // 프로젝트 ID (FK)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")  // 작성자 ID (FK)
    private Member reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")  // 대상자 ID (FK, Project의 user_id)
    private Member reviewee;

    private Integer rating;  // 평가 점수
}
