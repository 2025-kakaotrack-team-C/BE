package com.kakaotrack.pin.domain;

import com.kakaotrack.pin.comment.dto.AddCommentRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    // pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long commentId;

    // 프로젝트 id
    private Long projectId;

    // 댓글 내용
    @Column(name = "content")
    private String content;

    // 생성 시간
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    // 작성자 닉네임
    @Column(name = "writer")
    private String writer;


    public Comment(AddCommentRequest request, String writer) {
        this.projectId = request.getProjectId();
        this.content = request.getContent();
        this.writer = writer;
    }
}
