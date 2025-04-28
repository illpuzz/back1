package com.example.back.controller;

import com.example.back.model.Review;
import com.example.back.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 創建評價
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // 獲取評價
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(
            @PathVariable("id") Integer id,
            @RequestParam(value = "userId", required = false) Integer userId) {
        Review review = reviewService.getReview(id, userId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // 更新評價
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable("id") Integer id,
            @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // 刪除評價
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteReview(
            @PathVariable("id") Integer id,
            @RequestParam("userId") Integer userId) {
        reviewService.deleteReview(id, userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
 // 切換回覆可見性
    @PutMapping("/{id}/reply-visibility")
    public ResponseEntity<Review> toggleReplyVisibility(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, Boolean> payload) {
        Boolean isVisible = payload.get("isVisible");
        
        Review updatedReview;
        if (isVisible) {
            // 如果設置為可見，使用 restoreReplyVisibility
            updatedReview = reviewService.restoreReplyVisibility(id);
        } else {
            // 如果設置為不可見，使用 setReplyVisibility
            updatedReview = reviewService.setReplyVisibility(id, false);
        }
        
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // 管理員回覆評價
    @PutMapping("/{id}/reply")
    public ResponseEntity<Review> replyToReview(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> payload) {
        String replyText = payload.get("replyText");
        Review updatedReview = reviewService.replyToReview(id, replyText);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // 刪除評價回覆
    @DeleteMapping("/{id}/reply")
    public ResponseEntity<Map<String, Boolean>> deleteReply(
            @PathVariable("id") Integer id) {
        reviewService.deleteReply(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 點讚/取消點讚評價
    @PostMapping("/{id}/like")
    public ResponseEntity<Map<String, Boolean>> toggleLike(
            @PathVariable("id") Integer id,
            @RequestParam("userId") Integer userId) {
        reviewService.toggleLike(id, userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 分頁查詢評價
    @GetMapping
    public Page<Review> getAllReviews(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer campSiteId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "false") Boolean includeReported) {
        
        // 調用修改後的 searchReviews 方法，傳遞 includeReported 參數
        return reviewService.searchReviews(keyword, campSiteId, minRating, userId, page, size, sortBy, direction, includeReported);
    }

    // 獲取營地平均評分
    @GetMapping("/average-rating/{campSiteId}")
    public ResponseEntity<Map<String, Double>> getAverageRating(
            @PathVariable("campSiteId") Integer campSiteId) {
        Double avgRating = reviewService.getAverageRatingByCampSite(campSiteId);
        Map<String, Double> response = new HashMap<>();
        response.put("averageRating", avgRating);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}