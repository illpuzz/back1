package com.example.back.repository;

import com.example.back.model.ReviewReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Integer> {
    
    // 根據評價ID查詢所有舉報
    List<ReviewReport> findByReviewId(Integer reviewId);
    
    // 查詢特定使用者對評價的舉報
    Optional<ReviewReport> findByUserIdAndReviewId(Integer userId, Integer reviewId);
    
    // 根據狀態查詢舉報
    List<ReviewReport> findByStatus(String status);
    
    // 根據評價ID和舉報目標查詢舉報
    List<ReviewReport> findByReviewIdAndReportTarget(Integer reviewId, String reportTarget);
    
    // 查詢特定使用者對評價的特定目標舉報 (評論或回覆)
    Optional<ReviewReport> findByUserIdAndReviewIdAndReportTarget(Integer userId, Integer reviewId, String reportTarget);
    
    // 根據舉報目標查詢所有舉報
    List<ReviewReport> findByReportTarget(String reportTarget);
    
    // 根據狀態和舉報目標查詢舉報
    List<ReviewReport> findByStatusAndReportTarget(String status, String reportTarget);
    
    // 根據評價ID刪除所有舉報
    @Transactional
    void deleteByReviewId(Integer reviewId);
}