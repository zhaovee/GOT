package com.got.model;

public class SPMResult {
    private int id;
    private String subject;
    private String grade;
    private boolean pass;

    // Getters/Setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public boolean isPass() { return pass; }
    public void setPass(boolean pass) { this.pass = pass; }
}