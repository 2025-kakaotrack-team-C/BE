package com.kakaotrack.pin.application.dto;

import com.kakaotrack.pin.jwt.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
