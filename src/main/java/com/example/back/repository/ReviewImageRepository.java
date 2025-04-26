package com.example.back.repository;

import com.example.back.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
    
    // 根據評價ID查詢所有圖片
    List<ReviewImage> findByReviewId(Integer reviewId);
    
    // 根據評價ID刪除所有圖片
    void deleteByReviewId(Integer reviewId);
}