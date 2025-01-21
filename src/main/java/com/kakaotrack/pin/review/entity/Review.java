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
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "user_id")  // Member의 user_id를 참조
    private Member reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id", referencedColumnName = "user_id")  // Member의 user_id를 참조
    private Member reviewee;

    private Integer rating;
}
