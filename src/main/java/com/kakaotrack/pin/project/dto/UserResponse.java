package com.kakaotrack.pin.project.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String nickname;

    public UserResponse(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
}
