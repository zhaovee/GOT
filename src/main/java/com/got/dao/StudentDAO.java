package com.got.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.got.model.Student;

@Repository
public class StudentDAO {
    
    // Automatically inject the JdbcTemplate bean managed by Spring configuration
    @Autowired
    private JdbcTemplate db;

    // 1. Retrieve All Students
    // Fetch a complete list of students from the database
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM Students";
        // Map the result set to Student objects
        List<Student> list = db.query(sql, new BeanPropertyRowMapper<>(Student.class));
        
        // Iterate through each student to calculate their specific metrics (GPA and Status)
        for (Student s : list) {
            calculateStudentMetrics(s);
        }
        return list;
    }

    // 2. Search Students
    // Search for students by name or matrix number using a keyword
    public List<Student> searchStudents(String keyword) {
        String sql = "SELECT * FROM Students WHERE name LIKE ? OR matrix_no LIKE ?";
        // Add wildcards to the keyword for fuzzy matching
        String searchParam = "%" + keyword + "%";
        
        // Execute query with parameters
        List<Student> list = db.query(sql, new BeanPropertyRowMapper<>(Student.class), searchParam, searchParam);
        
        // Calculate metrics for the search results
        for (Student s : list) {
            calculateStudentMetrics(s);
        }
        return list;
    }

    // 3. Retrieve Single Student
    // Fetch a specific student details by their matrix number
    public Student getStudentByMatrix(String matrixNo) {
        String sql = "SELECT * FROM Students WHERE matrix_no = ?";
        try {
            // Execute query for a single object
            Student s = db.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), matrixNo);
            
            // Calculate metrics for this specific student
            calculateStudentMetrics(s);
            return s;
        } catch (EmptyResultDataAccessException e) {
            // Return null if the student is not found
            return null;
        }
    }

    // 4. Retrieve Students by Advisor
    // Fetch all students assigned to a specific advisor ID
    public List<Student> getStudentsByAdvisor(int advisorId) {
        String sql = "SELECT * FROM Students WHERE advisor_id = ?";
        List<Student> list = db.query(sql, new BeanPropertyRowMapper<>(Student.class), advisorId);
        
        // Calculate metrics for each student in the list
        for (Student s : list) {
            calculateStudentMetrics(s);
        }
        return list;
    }

    // 5. Core Logic: Calculate GPA and Academic Status
    // Helper method to compute GPA dynamically based on course records
    private void calculateStudentMetrics(Student s) {
        // Formula: Sum(GradePoint * Credit) / Sum(Credit)
        // Note: Only considers courses where a grade point exists (completed courses)
        // NULLIF is used to avoid division by zero error if total credits are 0
        String sql = "SELECT SUM(sc.grade_point * c.credit) / NULLIF(SUM(c.credit), 0) " +
                     "FROM StudentCourses sc " +
                     "JOIN Courses c ON sc.course_code = c.course_code " +
                     "WHERE sc.student_matrix = ? AND sc.grade_point IS NOT NULL";
        
        try {
            // Execute calculation query
            Double gpa = db.queryForObject(sql, Double.class, s.getMatrixNo());
            
            // Handle case where GPA might be null (e.g., no courses taken)
            if (gpa == null) gpa = 0.0;
            
            // Round GPA to two decimal places
            gpa = Math.round(gpa * 100.0) / 100.0;
            s.setGpa(gpa);

            // Determine Academic Status based on GPA
            // Logic: If GPA is between 0 and 2.0, status is "Probation". Otherwise "Active".
            if (gpa > 0 && gpa < 2.0) {
                s.setActiveCode("Probation");
            } else {
                s.setActiveCode("Active");
            }
        } catch (Exception e) {
            // Fallback in case of calculation errors
            s.setGpa(0.0);
            s.setActiveCode("Active");
        }
    }
}