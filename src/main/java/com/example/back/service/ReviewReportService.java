package com.example.back.service;

import com.example.back.model.ReviewReport;
import com.example.back.model.Review;
import com.example.back.repository.ReviewReportRepository;
import com.example.back.repository.ReviewRepository;
import com.example.back.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewReportService {

    @Autowired
    private ReviewReportRepository reportRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * 創建評論舉報
     */
    public ReviewReport createReviewReport(ReviewReport report) {
        Integer reviewId = report.getReviewId();
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("找不到評價 ID: " + reviewId));

        // 設置舉報目標為評論
        report.setReportTarget("review");
        
        // 將評論設為不可見（等待審核）
        review.setReviewIsVisible(false);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        
        return reportRepository.save(report);
    }
    
    /**
     * 創建回覆舉報
     */
    public ReviewReport createReplyReport(ReviewReport report) {
        Integer reviewId = report.getReviewId();
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("找不到評價 ID: " + reviewId));

        // 檢查評價是否有回覆
        if (review.getReplyText() == null || review.getReplyText().isEmpty()) {
            throw new RuntimeException("此評價沒有回覆內容可舉報");
        }
        
        // 設置舉報目標為回覆
        report.setReportTarget("reply");
        
        // 將回覆設為不可見（等待審核）
        review.setReplyIsVisible(false);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        
        return reportRepository.save(report);
    }
    
    /**
     * 創建舉報（兼容原有方法，根據reportTarget自動判斷舉報類型）
     */
    public ReviewReport createReport(ReviewReport report) {
        if (report.getReportTarget() == null || "review".equals(report.getReportTarget())) {
            return createReviewReport(report);
        } else if ("reply".equals(report.getReportTarget())) {
            return createReplyReport(report);
        } else {
            throw new RuntimeException("無效的舉報目標類型: " + report.getReportTarget());
        }
    }

    /**
     * 根據ID獲取舉報
     */
    public ReviewReport getReport(Integer reportId) {
        return reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("舉報不存在: " + reportId));
    }

    /**
     * 根據評價ID獲取所有舉報
     */
    public List<ReviewReport> getReportsByReviewId(Integer reviewId) {
        return reportRepository.findByReviewId(reviewId);
    }
    
    /**
     * 根據評價ID和舉報目標獲取所有舉報
     */
    public List<ReviewReport> getReportsByReviewIdAndTarget(Integer reviewId, String reportTarget) {
        return reportRepository.findByReviewIdAndReportTarget(reviewId, reportTarget);
    }

    /**
     * 查詢使用者是否已舉報評價
     */
    public boolean hasUserReportedReview(Integer userId, Integer reviewId) {
        return reportRepository.findByUserIdAndReviewId(userId, reviewId).isPresent();
    }
    
    /**
     * 查詢使用者是否已舉報評價的特定部分（評論或回覆）
     */
    public boolean hasUserReportedTarget(Integer userId, Integer reviewId, String reportTarget) {
        return reportRepository.findByUserIdAndReviewIdAndReportTarget(userId, reviewId, reportTarget).isPresent();
    }

    /**
     * 獲取所有待處理的舉報
     */
    public List<ReviewReport> getPendingReports() {
        return reportRepository.findByStatus("pending");
    }
    
    /**
     * 獲取特定目標類型的待處理舉報
     */
    public List<ReviewReport> getPendingReportsByTarget(String reportTarget) {
        return reportRepository.findByStatusAndReportTarget("pending", reportTarget);
    }

    /**
     * 處理舉報
     */
    @Transactional
    public ReviewReport processReport(Integer reportId, String status, String handlerNote) {
        ReviewReport report = getReport(reportId);
        
        // 更新舉報狀態和處理備註
        report.setStatus(status);
        report.setHandlerNote(handlerNote);
        
        // 如果舉報被駁回（拒絕），恢復被舉報內容的可見性
        if ("rejected".equals(status)) {
            Review review = reviewRepository.findById(report.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException("找不到評價 ID: " + report.getReviewId()));
            
            if ("review".equals(report.getReportTarget())) {
                // 直接設置評論可見性
                review.setReviewIsVisible(true);
            } else if ("reply".equals(report.getReportTarget())) {
                // 直接設置回覆可見性
                review.setReplyIsVisible(true);
            }
            
            review.setUpdatedAt(LocalDateTime.now());
            reviewRepository.save(review);
        }
        
        return reportRepository.save(report);
    }
    
    /**
     * 處理並移除被舉報的內容（確認舉報有效）
     */
    @Transactional
    public ReviewReport approveAndRemoveReportedContent(Integer reportId, String handlerNote) {
        ReviewReport report = getReport(reportId);
        
        // 更新舉報狀態為已批准
        report.setStatus("approved");
        report.setHandlerNote(handlerNote);
        
        // 處理被舉報的內容
        Review review = reviewRepository.findById(report.getReviewId())
            .orElseThrow(() -> new ReviewNotFoundException("找不到評價 ID: " + report.getReviewId()));
        
        if ("review".equals(report.getReportTarget())) {
            // 永久刪除評論
            reviewRepository.deleteById(report.getReviewId());
        } else if ("reply".equals(report.getReportTarget())) {
            // 刪除回覆
            review.setReplyText(null);
            review.setReplyIsVisible(true); // 重置可見性，雖然內容已清空
            review.setUpdatedAt(LocalDateTime.now());
            reviewRepository.save(review);
        }
        
        return reportRepository.save(report);
    }
    
    /**
     * 刪除評價相關的所有舉報
     */
    @Transactional
    public void deleteReportsByReviewId(Integer reviewId) {
        reportRepository.deleteByReviewId(reviewId);
    }
}