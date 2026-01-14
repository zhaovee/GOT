package com.got.model;

public class Student {
    private String matrixNo;
    private String name;
    private String program;
    private String gender;
    private String religion;
    private String nationality;
    private String email;
    private String phone;
    private String currentYear;
    private String intakeSession;
    private int advisorId;
    
    
    private double gpa; 
    private String activeCode; // e.g., "Active", "Probation"

    public Student() {}

    // Getters and Setters 
    public String getMatrixNo() { return matrixNo; }
    public void setMatrixNo(String matrixNo) { this.matrixNo = matrixNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCurrentYear() { return currentYear; }
    public void setCurrentYear(String currentYear) { this.currentYear = currentYear; }
    public String getIntakeSession() { return intakeSession; }
    public void setIntakeSession(String intakeSession) { this.intakeSession = intakeSession; }
    public int getAdvisorId() { return advisorId; }
    public void setAdvisorId(int advisorId) { this.advisorId = advisorId; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public String getActiveCode() { return activeCode; }
    public void setActiveCode(String activeCode) { this.activeCode = activeCode; }
}