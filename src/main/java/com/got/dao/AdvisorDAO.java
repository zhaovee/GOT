package com.got.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.got.model.Advisor;

@Repository
public class AdvisorDAO {
    
    // Automatically inject the JdbcTemplate bean managed by Spring configuration
    @Autowired
    private JdbcTemplate db;

    // 1. Login Verification
    // Authenticate an advisor by checking the username and password against the database
    public Advisor login(String username, String password) {
        String sql = "SELECT * FROM Advisors WHERE username = ? AND password = ?";
        try {
            // Execute the query using JdbcTemplate
            // Use BeanPropertyRowMapper to automatically map the result set columns to the Advisor object properties
            return db.queryForObject(
                sql, 
                new BeanPropertyRowMapper<>(Advisor.class), 
                username, password
            );
        } catch (EmptyResultDataAccessException e) {
            // Return null if no record is found (Invalid username or password)
            return null; 
        }
    }

    // 2. Update Password
    // Update the password for a specific advisor identified by their username
    public boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE Advisors SET password = ? WHERE username = ?";
        
        // Execute the update statement and get the number of affected rows
        int rows = db.update(sql, newPassword, username);
        
        // Return true if at least one row was updated, indicating success
        return rows > 0;
    }
    
    // 3. Retrieve Advisor Details
    // Fetch the full details of an advisor using their username
    public Advisor getAdvisorByUsername(String username) {
        String sql = "SELECT * FROM Advisors WHERE username = ?";
        try {
            // Execute the query and map the single result to an Advisor object
            return db.queryForObject(sql, new BeanPropertyRowMapper<>(Advisor.class), username);
        } catch (EmptyResultDataAccessException e) {
            // Return null if the username does not exist in the database
            return null;
        }
    }
}
