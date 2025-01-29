package com.kakaotrack.pin.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kakaotrack.pin.review.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 1. 내가 받은 모든 리뷰 조회
    List<Review> findAllByRevieweeId(Long userId);

    // 2. 특정 프로젝트에서 받은 리뷰 조회
    List<Review> findAllByProject_ProjectIdAndReviewee_Id(Long projectId, Long userId);

    // ReviewRepository
    List<Review> findByRevieweeId(Long userId);
    Long countByProject_ProjectId(Long projectId);
}
