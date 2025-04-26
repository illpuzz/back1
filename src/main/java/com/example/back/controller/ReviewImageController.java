package com.example.back.controller;

import com.example.back.model.ReviewImage;
import com.example.back.service.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review-images")
@CrossOrigin(origins = "*")
public class ReviewImageController {

    @Autowired
    private ReviewImageService imageService;

    // 上傳單個評價圖片
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "reviewId", required = false) Integer reviewId) {
        Map<String, String> response = imageService.uploadImage(file, reviewId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 批次上傳評價圖片
    @PostMapping("/upload-multiple")
    public ResponseEntity<List<Map<String, String>>> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "reviewId", required = false) Integer reviewId) {
        List<Map<String, String>> response = imageService.uploadMultipleImages(files, reviewId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 獲取評價的所有圖片
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewImage>> getImagesByReviewId(
            @PathVariable("reviewId") Integer reviewId) {
        List<ReviewImage> images = imageService.getImagesByReviewId(reviewId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    // 刪除評價圖片
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable("id") Integer id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 刪除評價的所有圖片
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteByReviewId(
            @PathVariable("reviewId") Integer reviewId) {
        imageService.deleteByReviewId(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}