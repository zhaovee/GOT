package com.got.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.got.model.SPMResult;

@Repository
public class SPMDAO {
    
    // Automatically inject the JdbcTemplate bean managed by Spring configuration
    @Autowired
    private JdbcTemplate db;

    // 1. Fetch SPM Results (Automatic Mapping)
    // Retrieve all SPM subject results for a specific student using BeanPropertyRowMapper
    public List<SPMResult> getResultsByStudent(String matrixNo) {
        // Define SQL query to select specific columns
        // Note: 'subject_name' is aliased as 'subject' to match the property name in the SPMResult model
        String sql = "SELECT id, student_matrix, subject_name AS subject, grade, is_pass " +
                     "FROM SPMResults WHERE student_matrix = ?";
        
        // Execute the query and automatically map the result set to a list of SPMResult objects
        return db.query(sql, new BeanPropertyRowMapper<>(SPMResult.class), matrixNo);
    }
    
    // 2. Fetch SPM Results (Manual Mapping)
    // Retrieve SPM results for a student using manual RowMapper logic (Lambda expression)
    public List<SPMResult> getSPMResults(String matrixNo) {
        // Define the SQL query to select all columns
        String sql = "SELECT * FROM SPMResults WHERE student_matrix = ?";
        
        // Execute the query using standard JDBC template methods with Lambdas
        return db.query(
            sql, 
            // PreparedStatementSetter: Set the parameters for the query
            ps -> {
                ps.setString(1, matrixNo);
            }, 
            // RowMapper: Map each row of the ResultSet to an SPMResult object
            (rs, rowNum) -> {
                SPMResult res = new SPMResult();
                // Manually map database columns to object properties
                res.setSubject(rs.getString("subject_name"));
                res.setGrade(rs.getString("grade"));
                res.setPass(rs.getBoolean("is_pass"));
                return res;
            }
        );
    }
}