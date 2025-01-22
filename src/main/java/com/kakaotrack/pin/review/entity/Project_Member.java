package com.kakaotrack.pin.review.entity;

import com.kakaotrack.pin.domain.Project;
import com.kakaotrack.pin.jwt.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    private Integer department; // modify
}