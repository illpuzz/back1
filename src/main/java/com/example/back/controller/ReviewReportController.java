package com.example.back.controller;

import com.example.back.model.ReviewReport;
import com.example.back.service.ReviewReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review-reports")
@CrossOrigin(origins = "*")
public class ReviewReportController {

    @Autowired
    private ReviewReportService reportService;

    // 創建評論舉報
    @PostMapping("/review")
    public ResponseEntity<ReviewReport> createReviewReport(@RequestBody ReviewReport report) {
        ReviewReport createdReport = reportService.createReviewReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }
    
    // 創建回覆舉報
    @PostMapping("/reply")
    public ResponseEntity<ReviewReport> createReplyReport(@RequestBody ReviewReport report) {
        ReviewReport createdReport = reportService.createReplyReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }
    
    // 兼容原有方法，創建舉報
    @PostMapping
    public ResponseEntity<ReviewReport> createReport(@RequestBody ReviewReport report) {
        ReviewReport createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // 獲取舉報
    @GetMapping("/{id}")
    public ResponseEntity<ReviewReport> getReport(@PathVariable("id") Integer id) {
        ReviewReport report = reportService.getReport(id);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    // 獲取評價的所有舉報
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewReport>> getReportsByReviewId(
            @PathVariable("reviewId") Integer reviewId,
            @RequestParam(value = "target", required = false) String reportTarget) {
        
        List<ReviewReport> reports;
        if (reportTarget != null && !reportTarget.isEmpty()) {
            // 如果提供了舉報目標參數，則按目標類型過濾
            reports = reportService.getReportsByReviewIdAndTarget(reviewId, reportTarget);
        } else {
            // 否則返回所有舉報
            reports = reportService.getReportsByReviewId(reviewId);
        }
        
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // 檢查使用者是否已舉報評價
    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserReportedReview(
            @RequestParam("userId") Integer userId,
            @RequestParam("reviewId") Integer reviewId,
            @RequestParam(value = "target", required = false) String reportTarget) {
        
        boolean hasReported;
        if (reportTarget != null && !reportTarget.isEmpty()) {
            // 檢查特定目標類型的舉報
            hasReported = reportService.hasUserReportedTarget(userId, reviewId, reportTarget);
        } else {
            // 檢查任何類型的舉報
            hasReported = reportService.hasUserReportedReview(userId, reviewId);
        }
        
        return new ResponseEntity<>(hasReported, HttpStatus.OK);
    }

    // 處理舉報
    @PutMapping("/{id}/process")
    public ResponseEntity<ReviewReport> processReport(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> payload) {
        
        String status = payload.get("status");
        String handlerNote = payload.get("handlerNote");
        
        ReviewReport updatedReport = reportService.processReport(id, status, handlerNote);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }
    
    // 核准舉報並移除內容
    @PutMapping("/{id}/approve-and-remove")
    public ResponseEntity<ReviewReport> approveAndRemoveContent(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> payload) {
        
        String handlerNote = payload.get("handlerNote");
        
        ReviewReport updatedReport = reportService.approveAndRemoveReportedContent(id, handlerNote);
        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    // 獲取待處理的舉報
    @GetMapping("/pending")
    public ResponseEntity<List<ReviewReport>> getPendingReports(
            @RequestParam(value = "target", required = false) String reportTarget) {
        
        List<ReviewReport> pendingReports;
        if (reportTarget != null && !reportTarget.isEmpty()) {
            // 根據目標類型過濾待處理舉報
            pendingReports = reportService.getPendingReportsByTarget(reportTarget);
        } else {
            // 獲取所有待處理舉報
            pendingReports = reportService.getPendingReports();
        }
        
        return new ResponseEntity<>(pendingReports, HttpStatus.OK);
    }
    
    // 獲取特定目標類型的所有舉報
    @GetMapping("/by-target")
    public ResponseEntity<List<ReviewReport>> getReportsByTarget(
            @RequestParam("target") String reportTarget) {
        
        List<ReviewReport> reports = reportService.getPendingReportsByTarget(reportTarget);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}