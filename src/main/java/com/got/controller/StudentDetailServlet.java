package com.got.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.StudentDAO;
import com.got.model.Student;

@WebServlet("/StudentDetail")
public class StudentDetailServlet extends HttpServlet {
    
    // Initialize Data Access Object (DAO) for handling student database operations
    private StudentDAO studentDAO = new StudentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve Request Parameter
        // Get the matrix number from the query string (e.g., ?matrixNo=...)
        String matrixNo = request.getParameter("matrixNo");
        
        Student foundStudent = null;

        // 2. Fetch Student Data
        // Check if the matrix number provided is valid (not null or empty)
        if (matrixNo != null && !matrixNo.isEmpty()) {
            // Query the database to find the student details using the matrix number
            foundStudent = studentDAO.getStudentByMatrix(matrixNo);
        }
        
        // 3. Prepare Data for View
        // Set the student object as a request attribute to be used in the JSP
        request.setAttribute("student", foundStudent);
        
        // Set the active page identifier to keep the "Student List" sidebar item highlighted
        request.setAttribute("activePage", "StudentList"); 

        // 4. Render the Page
        // Forward the request to the detail JSP for display
        request.getRequestDispatcher("StudentDetail.jsp").forward(request, response);
    }
}