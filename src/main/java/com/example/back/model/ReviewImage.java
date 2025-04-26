package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_images")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "review_id")
    private Integer reviewId;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}