package com.example.back.service;

import com.example.back.model.ReviewReport;
import com.example.back.repository.ReviewReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.back.model.Review;
import com.example.back.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewReportService {

    @Autowired
    private ReviewReportRepository reportRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    // 創建舉報
    public ReviewReport createReport(ReviewReport report) {
        Integer reviewId = report.getReviewId();
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("找不到評價 ID: " + reviewId));

        review.setReviewIsVisible(false);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        return reportRepository.save(report);
    }

    // 根據ID獲取舉報
    public ReviewReport getReport(Integer reportId) {
        return reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("舉報不存在: " + reportId));
    }

    // 根據ID獲取舉報 (保留原有方法)
    public Optional<ReviewReport> getReportById(Integer id) {
        return reportRepository.findById(id);
    }

    // 根據評價ID獲取所有舉報
    public List<ReviewReport> getReportsByReviewId(Integer reviewId) {
        return reportRepository.findByReviewId(reviewId);
    }

    // 查詢使用者是否已舉報評價
    public boolean hasUserReportedReview(Integer userId, Integer reviewId) {
        return reportRepository.findByUserIdAndReviewId(userId, reviewId).isPresent();
    }

    // 獲取所有待處理的舉報
    public List<ReviewReport> getPendingReports() {
        return reportRepository.findByStatus("pending");
    }

    // 處理舉報
    public ReviewReport processReport(Integer reportId, String status, String handlerNote) {
        ReviewReport report = getReport(reportId);
        report.setStatus(status);
        
        // 如果您的實體類有 handlerNote 欄位，可以設置處理備註
        // report.setHandlerNote(handlerNote);
        return reportRepository.save(report);
    }
    
    // 刪除評價相關的所有舉報
    @Transactional
    public void deleteReportsByReviewId(Integer reviewId) {
        List<ReviewReport> reports = reportRepository.findByReviewId(reviewId);
        for (ReviewReport report : reports) {
            reportRepository.delete(report);
        }
        // 或者直接使用批量刪除方法（如果存在）
        // reportRepository.deleteByReviewId(reviewId);
    }
}