package com.kakaotrack.pin.mypage.entity;

import com.kakaotrack.pin.jwt.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userDepartmentId;  // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // FK
    private Member member;

    private int department;
}