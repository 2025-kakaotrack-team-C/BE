package com.kakaotrack.pin.comment.dto;

import com.kakaotrack.pin.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest {

    private Long projectId;
    private String content;


}
