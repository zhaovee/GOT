package com.got.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.got.model.Application;
import com.got.model.SPMResult;
import com.got.util.DBManager;

public class ApplicationDAO {
    
    // Initialize JdbcTemplate using the DBManager for database connectivity
    private JdbcTemplate db = DBManager.getJdbcTemplate();

    // 1. Retrieve All Applications
    // Fetch a list of all applications, ordered by the application date in descending order
    public List<Application> getAllApplications() {
        // Define SQL query to join Applications table with Students table to get student names
        String sql = "SELECT a.*, s.name as student_name FROM Applications a " +
                     "JOIN Students s ON a.student_matrix = s.matrix_no " +
                     "ORDER BY a.apply_date DESC";
        
        // Execute query and map the ResultSet to Application objects
        return db.query(sql, (rs, rowNum) -> {
            Application app = new Application();
            app.setAppId(rs.getInt("app_id"));
            app.setStudentMatrix(rs.getString("student_matrix"));
            app.setStudentName(rs.getString("student_name"));
            app.setType(rs.getString("type"));
            app.setStatus(rs.getString("status"));
            app.setApplyDate(rs.getTimestamp("apply_date"));
            app.setAdminComment(rs.getString("admin_comment"));
            return app;
        });
    }

    // 2. Retrieve Single Application Detail
    // Fetch a specific application by its ID
    public Application getApplicationById(int id) {
        // Define SQL query to select application details joined with student info
        String sql = "SELECT a.*, s.name as student_name FROM Applications a " +
                     "JOIN Students s ON a.student_matrix = s.matrix_no WHERE app_id = ?";
                     
        // Execute query for a single object and map the result
        return db.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Application app = new Application();
            app.setAppId(rs.getInt("app_id"));
            app.setStudentMatrix(rs.getString("student_matrix"));
            app.setStudentName(rs.getString("student_name"));
            app.setType(rs.getString("type"));
            app.setStatus(rs.getString("status"));
            return app;
        });
    }

    // 3. Process Application (Update Status + Notify)
    // Update the status of an application and send a notification to the student
    public void processApplication(int appId, String status, String matrixNo, String message) {
        // Step 1: Update the application status in the database
        String sqlUpdate = "UPDATE Applications SET status = ? WHERE app_id = ?";
        db.update(sqlUpdate, status, appId);

        // Step 2: Send a notification message to the student regarding the update
        sendNotification(matrixNo, message);
    }
    
    // 4. Send Notification
    // Insert a new notification record into the database (Used for updates or academic evaluations)
    public void sendNotification(String matrixNo, String message) {
        String sql = "INSERT INTO Notifications (student_matrix, message) VALUES (?, ?)";
        db.update(sql, matrixNo, message);
    }

    // 5. Retrieve Latest Notification
    // Fetch the most recent notification or evaluation for a specific student
    public String getLatestNotification(String matrixNo) {
        // Define SQL query using TOP 1 (SQL Server syntax) to get the latest record
        String sql = "SELECT TOP 1 message FROM Notifications WHERE student_matrix = ? ORDER BY created_at DESC";
        try {
            // Execute query expecting a single String result
            return db.queryForObject(sql, String.class, matrixNo);
        } catch (EmptyResultDataAccessException e) {
            // Return an empty string if no notification is found for the student
            return ""; 
        }
    }
}