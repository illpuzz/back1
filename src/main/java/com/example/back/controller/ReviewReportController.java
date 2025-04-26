package com.example.back.controller;

import com.example.back.model.ReviewReport;
import com.example.back.service.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review-reports")
@CrossOrigin(origins = "*")
public class ReviewReportController {

    @Autowired
    private ReviewReportService reportService;

    // 創建舉報
    @PostMapping
    public ResponseEntity<ReviewReport> createReport(@RequestBody ReviewReport report) {
        ReviewReport createdReport = reportService.createReport(report);
        return ResponseEntity.ok(createdReport);
    }

    // 獲取舉報
    @GetMapping("/{id}")
    public ResponseEntity<ReviewReport> getReport(@PathVariable("id") Integer id) {
        // 確保轉換為 Integer 類型
        ReviewReport report = reportService.getReport(id);
        return ResponseEntity.ok(report);
    }

    // 獲取評價的所有舉報
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewReport>> getReportsByReviewId(@PathVariable("reviewId") Integer reviewId) {
        List<ReviewReport> reports = reportService.getReportsByReviewId(reviewId);
        return ResponseEntity.ok(reports);
    }

    // 檢查使用者是否已舉報評價
    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserReportedReview(
            @RequestParam("userId") Integer userId,
            @RequestParam("reviewId") Integer reviewId) {
        boolean hasReported = reportService.hasUserReportedReview(userId, reviewId);
        return ResponseEntity.ok(hasReported);
    }

    // 處理舉報
    @PutMapping("/{id}/process")
    public ResponseEntity<ReviewReport> processReport(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        String handlerNote = payload.get("handlerNote");
        ReviewReport updatedReport = reportService.processReport(id, status, handlerNote);
        return ResponseEntity.ok(updatedReport);
    }

    // 獲取待處理的舉報
    @GetMapping("/pending")
    public ResponseEntity<List<ReviewReport>> getPendingReports() {
        List<ReviewReport> pendingReports = reportService.getPendingReports();
        return ResponseEntity.ok(pendingReports);
    }
}