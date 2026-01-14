package com.got.model;

import java.util.Date;

public class Application {
    private int appId;
    private String studentMatrix;
    private String studentName;
    private String type; // GRADUATION / INTERNSHIP
    private String status; // PENDING, APPROVED, REJECTED
    private Date applyDate;
    private String adminComment;

    // Constructors, Getters, Setters...
    public Application() {}
    
    // Getters/Setters 
    public int getAppId() { return appId; }
    public void setAppId(int appId) { this.appId = appId; }
    public String getStudentMatrix() { return studentMatrix; }
    public void setStudentMatrix(String studentMatrix) { this.studentMatrix = studentMatrix; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }
    public String getAdminComment() { return adminComment; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }
}