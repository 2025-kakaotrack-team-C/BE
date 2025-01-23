package com.kakaotrack.pin.comment.service;

import com.kakaotrack.pin.comment.dto.AddCommentRequest;
import com.kakaotrack.pin.comment.repository.CommentRepository;
import com.kakaotrack.pin.domain.Comment;
import com.kakaotrack.pin.jwt.member.Member;
import com.kakaotrack.pin.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    // 댓글 저장
    public void saveComment(AddCommentRequest request, String username) {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("member not found: " + username));

        // setWriter = 로그인 된 사람
        Comment comment = new Comment(request, member.getNickname());

        // 저장
        commentRepository.save(comment);
    }
}
