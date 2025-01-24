package com.kakaotrack.pin.comment.controller;

import com.kakaotrack.pin.comment.dto.AddCommentRequest;
import com.kakaotrack.pin.comment.service.CommentService;
import com.kakaotrack.pin.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("api/comment/{id}")    // 프로젝트 아이디
    public void save(@PathVariable Long id, @RequestBody String contents) {

        // 로그인 된 유저 정보 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 프로젝트 아이디, 댓글 내용 dto에 저장
        AddCommentRequest addCommentRequest = new AddCommentRequest(id, contents);

        commentService.saveComment(addCommentRequest, username);
    }
}
