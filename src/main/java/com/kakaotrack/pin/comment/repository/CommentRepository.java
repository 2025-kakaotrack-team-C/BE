package com.kakaotrack.pin.comment.repository;

import com.kakaotrack.pin.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
