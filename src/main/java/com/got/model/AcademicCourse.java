package com.got.model;

public class AcademicCourse {
    private String code;     // course_code
    private String name;     // course_name
    private String status;   // status
    private int credit;      // credit
    private String semester; // semester

    public AcademicCourse() {}

    public AcademicCourse(String code, String name, String status, int credit) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.credit = credit;
    }
    
    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}
