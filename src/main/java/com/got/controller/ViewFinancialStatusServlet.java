package com.got.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.FinanceDAO;
import com.got.dao.StudentDAO;
import com.got.model.FinancialRecord;
import com.got.model.Student;

@WebServlet("/ViewFinancialStatus")
public class ViewFinancialStatusServlet extends HttpServlet {
    
    // Initialize Data Access Objects (DAOs) for database interactions
    private StudentDAO studentDAO = new StudentDAO();
    private FinanceDAO financeDAO = new FinanceDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve Request Parameters
        // Get the student ID from the URL parameter (e.g., ?studentId=...)
        String studentId = request.getParameter("studentId");
        
        // Set the active page identifier for sidebar highlighting
        request.setAttribute("activePage", "ViewFinancialStatus");

        if (studentId == null) {
            // Case 1: No Student Selected (List View)
            // If no student ID is provided, fetch the list of all students from the database
            List<Student> list = studentDAO.getAllStudents();
            
            // Set the student list as a request attribute
            request.setAttribute("studentList", list);
            
            // Define the target URL for the student selector to redirect back to this servlet
            request.setAttribute("targetUrl", "ViewFinancialStatus"); 
            
            // Forward the request to the main list JSP page
            request.getRequestDispatcher("ViewFinancialStatus.jsp").forward(request, response);
        } else {
            // Case 2: Student Selected (Detail View)
            // Fetch specific student details using the provided matrix number
            Student target = studentDAO.getStudentByMatrix(studentId);
            
            if (target != null) {
                // Fetch financial records for the student using the FinanceDAO
                List<FinancialRecord> records = financeDAO.getRecordsByStudent(studentId);
                
                // Calculate the total outstanding debt
                // Positive amount indicates debt, negative indicates credit/balance
                double totalAmount = 0;
                if (records != null) {
                    for(FinancialRecord r : records) {
                        totalAmount += r.getAmount();
                    }
                }

                // Set attributes to be displayed on the detail page
                request.setAttribute("student", target);
                request.setAttribute("records", records);
                request.setAttribute("totalAmount", totalAmount);
                
                // Forward the request to the detail JSP page
                request.getRequestDispatcher("ViewFinancialStatusDetail.jsp").forward(request, response);
            } else {
                // If the student ID is invalid or not found, redirect back to the list view
                response.sendRedirect("ViewFinancialStatus");
            }
        }
    }
}