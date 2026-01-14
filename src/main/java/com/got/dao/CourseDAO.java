package com.got.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.got.model.AcademicCourse;
import com.got.util.DBManager;

public class CourseDAO {
    
    // Initialize JdbcTemplate using the DBManager for database connectivity
    private JdbcTemplate db = DBManager.getJdbcTemplate();

    // 1. Retrieve All Courses (Catalog)
    // Fetch a list of all available courses from the system
    public List<AcademicCourse> getAllCourses() {
        // Define SQL query to select course details (code, name, credit)
        String sql = "SELECT course_code AS code, course_name AS name, credit FROM Courses";
        
        // Execute the query and map the results to AcademicCourse objects
        return db.query(sql, new BeanPropertyRowMapper<>(AcademicCourse.class));
    }

    // 2. Search Courses
    // Find courses based on a search keyword matching the course code or name
    public List<AcademicCourse> searchCourses(String query) {
        // Define SQL query with wildcards for fuzzy matching
        String sql = "SELECT course_code AS code, course_name AS name, credit FROM Courses WHERE course_code LIKE ? OR course_name LIKE ?";
        
        // Prepare the search parameter with percent signs for the LIKE clause
        String param = "%" + query + "%";
        
        // Execute the query with the search parameter applied to both columns
        return db.query(sql, new BeanPropertyRowMapper<>(AcademicCourse.class), param, param);
    }

    // 3. Retrieve Student's Registered Courses
    // Fetch the list of courses a specific student has registered for
    // Includes sorting logic to organize by semester sequence
    public List<AcademicCourse> getStudentCourses(String matrixNo) {
        // Define SQL query joining StudentCourses and Courses tables
        // Selects course details along with the specific status and semester for the student
        String sql = "SELECT c.course_code AS code, c.course_name AS name, c.credit, sc.status, sc.semester " +
                "FROM StudentCourses sc " +
                "JOIN Courses c ON sc.course_code = c.course_code " +
                "WHERE sc.student_matrix = ? " +
                "ORDER BY sc.semester ASC, c.course_code ASC"; // Order by semester then course code
        
        // Execute the query for the given student matrix number
        return db.query(sql, new BeanPropertyRowMapper<>(AcademicCourse.class), matrixNo);
    }

    // 4. Add Course for Student
    // Register a course for a student in a specific semester
    public void addCourseForStudent(String matrixNo, String courseCode, String semester) {
        // Step 1: Check for Duplicates
        // Verify if the student has already registered for this course to prevent primary key violations
        String checkSql = "SELECT COUNT(*) FROM StudentCourses WHERE student_matrix = ? AND course_code = ?";
        Integer count = db.queryForObject(checkSql, Integer.class, matrixNo, courseCode);
        
        // Step 2: Insert Record
        // If the course is not found (count is 0), insert the new record
        if (count != null && count == 0) {
            String sql = "INSERT INTO StudentCourses (student_matrix, course_code, semester, status) VALUES (?, ?, ?, 'PENDING')";
            db.update(sql, matrixNo, courseCode, semester);
        }
    }

    // 5. Remove Course
    // Delete a course registration record for a student
    public void removeCourseForStudent(String matrixNo, String courseCode) {
        String sql = "DELETE FROM StudentCourses WHERE student_matrix = ? AND course_code = ?";
        db.update(sql, matrixNo, courseCode);
    }

    // 6. Update Course Status
    // Modify the status (e.g., PASS, FAIL, PENDING) of a student's course
    // Used mainly in the Academic Progress module
    public void updateCourseStatus(String matrixNo, String courseCode, String newStatus) {
        String sql = "UPDATE StudentCourses SET status = ? WHERE student_matrix = ? AND course_code = ?";
        db.update(sql, newStatus, matrixNo, courseCode);
    }

    // 7. Calculate Total Earned Credits
    // Sum the credits of all courses where the student has achieved a 'PASS' status
    // Used for Qualification checks
    public int getTotalCreditsEarned(String matrixNo) {
        String sql = "SELECT SUM(c.credit) FROM StudentCourses sc " +
                     "JOIN Courses c ON sc.course_code = c.course_code " +
                     "WHERE sc.student_matrix = ? AND sc.status = 'PASS'";
        
        // Execute the query to get the sum
        Integer total = db.queryForObject(sql, Integer.class, matrixNo);
        
        // Return 0 if the result is null (e.g., student has no passed courses yet)
        return (total != null) ? total : 0;
    }
}