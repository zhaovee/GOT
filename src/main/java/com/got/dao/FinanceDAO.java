package com.got.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.got.model.FinancialRecord; 
import com.got.util.DBManager;

public class FinanceDAO {
    
    // Initialize JdbcTemplate using the DBManager for database connectivity
    private JdbcTemplate db = DBManager.getJdbcTemplate();

    // 1. Fetch Financial History
    // Retrieve a list of all financial records for a specific student identified by matrix number
    public List<FinancialRecord> getRecordsByStudent(String matrixNo) {
        // Define SQL query to fetch transaction details
        // 'item_name' is aliased to 'name' to match the model property
        // The date is formatted as 'dd/MM/yyyy' directly in the query for display consistency
        String sql = "SELECT item_name AS name, FORMAT(record_date, 'dd/MM/yyyy') AS date, description, amount " +
                     "FROM FinanceRecords WHERE student_matrix = ? ORDER BY record_date DESC";
        
        // Execute the query and automatically map the result set to FinancialRecord objects
        // This relies on the standard naming conventions or aliases matching the class properties
        return db.query(sql, new BeanPropertyRowMapper<>(FinancialRecord.class), matrixNo);
    }

    // 2. Calculate Total Debt
    // Calculate the total outstanding amount for unpaid records
    // A positive result indicates that the student has debt (based on business logic)
    public double getTotalDebt(String matrixNo) {
        // Define SQL query to sum the 'amount' field only where the status is 'UNPAID'
        String sql = "SELECT SUM(amount) FROM FinanceRecords WHERE student_matrix = ? AND status = 'UNPAID'";
        
        // Execute the query to get the total sum as a Double object
        Double total = db.queryForObject(sql, Double.class, matrixNo);
        
        // Handle potential null values (e.g., if there are no unpaid records)
        // Return 0.0 if null, otherwise return the calculated total
        return (total != null) ? total : 0.0;
    }
}