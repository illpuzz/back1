package com.example.back.repository;

import com.example.back.model.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Integer> {
    
    // 查詢特定使用者對評價的點讚狀態
    Optional<ReviewLike> findByUserIdAndReviewId(Integer userId, Integer reviewId);
    
    // 計算特定評價的點讚數
    @Query("SELECT COUNT(rl) FROM ReviewLike rl WHERE rl.reviewId = :reviewId AND rl.likeType = 1")
    Long countLikesByReviewId(@Param("reviewId") Integer reviewId);
    
    // 根據評價ID刪除所有點讚
    void deleteByReviewId(Integer reviewId);
}