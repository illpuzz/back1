package com.example.back.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review_reports")
public class ReviewReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "review_id")
    private Integer reviewId;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "role_id")
    private Integer roleId;
    
    @Column(name = "reason")
    private String reason;
    
    @Column(name = "report_type")
    private Integer reportType;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "status")
    private String status;
    
    // 舉報目標: "review" 表示舉報評論, "reply" 表示舉報回覆
    @Column(name = "report_target")
    private String reportTarget;
    
    // 處理者備註
    @Column(name = "handler_note")
    private String handlerNote;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = "pending"; // 默認狀態為待處理
        
        // 如果未設置舉報目標，預設為舉報評論
        if (this.reportTarget == null) {
            this.reportTarget = "review";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Integer getReportType() {
        return reportType;
    }
    
    public void setReportType(Integer reportType) {
        this.reportType = reportType;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getReportTarget() {
        return reportTarget;
    }
    
    public void setReportTarget(String reportTarget) {
        this.reportTarget = reportTarget;
    }
    
    public String getHandlerNote() {
        return handlerNote;
    }
    
    public void setHandlerNote(String handlerNote) {
        this.handlerNote = handlerNote;
    }
}