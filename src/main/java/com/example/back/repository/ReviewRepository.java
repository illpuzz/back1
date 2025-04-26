package com.example.back.repository;

import com.example.back.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    // 根據營地ID查詢評價
    Page<Review> findByCampSiteIdAndReviewIsVisibleTrue(Integer campSiteId, Pageable pageable);
    
    // 根據使用者ID查詢評價
    Page<Review> findByUserIdAndReviewIsVisibleTrue(Integer userId, Pageable pageable);
    
    // 關鍵字搜尋（在評價文字、優點和缺點中搜尋）
    @Query("SELECT r FROM Review r WHERE " +
            "(r.reviewText LIKE %:keyword% OR r.pros LIKE %:keyword% OR r.cons LIKE %:keyword%) " +
            "AND r.reviewIsVisible = true")
    Page<Review> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // 根據評分範圍查詢
    Page<Review> findByOverallRatingGreaterThanEqualAndReviewIsVisibleTrue(Integer minRating, Pageable pageable);
    
    // 統計指定營地的平均評分
    @Query("SELECT AVG(r.overallRating) FROM Review r WHERE r.campSiteId = :campSiteId AND r.reviewIsVisible = true")
    Double getAverageRatingByCampSiteId(@Param("campSiteId") Integer campSiteId);
}