package com.got.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.StudentDAO;
import com.got.model.Student;

@Controller
public class StudentDetailController {
    
    // Inject StudentDAO for retrieving specific student data
    @Autowired
    private StudentDAO studentDAO;

    // Handle GET requests to view student details
    @GetMapping("/StudentDetail")
    public String getStudentDetail(
            @RequestParam(value = "matrixNo", required = false) String matrixNo, 
            Model model) {
        
        Student foundStudent = null;

        // 1. Fetch Student Data
        // Check if the matrix number provided is valid
        if (matrixNo != null && !matrixNo.isEmpty()) {
            // Query the database to find the student details using the matrix number
            foundStudent = studentDAO.getStudentByMatrix(matrixNo);
        }
        
        // 2. Prepare Data for View
        // Add the student object to the model
        model.addAttribute("student", foundStudent);
        
        // Set the active page identifier to keep the "Student List" sidebar item highlighted
        model.addAttribute("activePage", "StudentList"); 

        // 3. Render the Page
        // Return the view name which resolves to StudentDetail.jsp
        return "StudentDetail";
    }
}