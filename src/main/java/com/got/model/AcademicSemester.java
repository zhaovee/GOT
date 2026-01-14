package com.got.model;
import java.util.List;

public class AcademicSemester {
    private String name; // e.g., "2023/2024 II"
    private List<AcademicCourse> courses;

    public AcademicSemester(String name, List<AcademicCourse> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() { return name; }
    public List<AcademicCourse> getCourses() { return courses; }
}