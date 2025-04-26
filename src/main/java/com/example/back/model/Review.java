package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "camp_site_id", nullable = false)
    private Integer campSiteId;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "review_text", columnDefinition = "TEXT", nullable = false)
    private String reviewText;
    
    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating;
    
    @Column(name = "cleanliness_rating")
    private Integer cleanlinessRating;
    
    @Column(name = "convenience_rating")
    private Integer convenienceRating;
    
    @Column(name = "friendliness_rating")
    private Integer friendlinessRating;
    
    @Column(name = "pros", columnDefinition = "TEXT")
    private String pros;
    
    @Column(name = "cons", columnDefinition = "TEXT")
    private String cons;
    
    @Column(name = "reply_text", columnDefinition = "TEXT")
    private String replyText;
    
    @Column(name = "like_count")
    private Long likeCount = 0L;
    
    @Column(name = "review_is_visible", nullable = false)
    private Boolean reviewIsVisible = true;
    
    @Transient
    private Integer userLikeStatus = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Transient
    private List<String> imageUrls;
    
    // Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getCampSiteId() {
        return campSiteId;
    }
    
    public void setCampSiteId(Integer campSiteId) {
        this.campSiteId = campSiteId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getReviewText() {
        return reviewText;
    }
    
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    
    public Integer getOverallRating() {
        return overallRating;
    }
    
    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }
    
    public Integer getCleanlinessRating() {
        return cleanlinessRating;
    }
    
    public void setCleanlinessRating(Integer cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }
    
    public Integer getConvenienceRating() {
        return convenienceRating;
    }
    
    public void setConvenienceRating(Integer convenienceRating) {
        this.convenienceRating = convenienceRating;
    }
    
    public Integer getFriendlinessRating() {
        return friendlinessRating;
    }
    
    public void setFriendlinessRating(Integer friendlinessRating) {
        this.friendlinessRating = friendlinessRating;
    }
    
    public String getPros() {
        return pros;
    }
    
    public void setPros(String pros) {
        this.pros = pros;
    }
    
    public String getCons() {
        return cons;
    }
    
    public void setCons(String cons) {
        this.cons = cons;
    }
    
    public String getReplyText() {
        return replyText;
    }
    
    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
    
    public Long getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
    
    public Boolean getReviewIsVisible() {
        return reviewIsVisible;
    }
    
    public void setReviewIsVisible(Boolean reviewIsVisible) {
        this.reviewIsVisible = reviewIsVisible;
    }
    
    public Integer getUserLikeStatus() {
        return userLikeStatus;
    }
    
    public void setUserLikeStatus(Integer userLikeStatus) {
        this.userLikeStatus = userLikeStatus;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}