package com.got.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.SPMDAO;
import com.got.dao.StudentDAO;
import com.got.model.SPMResult;
import com.got.model.Student;

@WebServlet("/SPMCheck")
public class SPMServlet extends HttpServlet {
    
    // Initialize Data Access Objects (DAOs) for database operations
    private SPMDAO spmDao = new SPMDAO();
    private StudentDAO studentDAO = new StudentDAO(); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the student ID from the request parameter
        String studentId = request.getParameter("studentId");
        
        // Set the active page for sidebar highlighting
        request.setAttribute("activePage", "SPMCheck");

        if (studentId == null) {
            // Case 1: No student selected (Display List Mode)
            // Fetch the list of all students using the StudentDAO
            List<Student> students = studentDAO.getAllStudents();
            
            // Set attributes to render the student list page
            request.setAttribute("studentList", students);
            request.setAttribute("targetUrl", "SPMCheck");
            
            // Forward the request to the student list JSP
            request.getRequestDispatcher("SPMList.jsp").forward(request, response);
        } else {
            // Case 2: A student is selected (Display Detail Mode)
            // Fetch the SPM examination results for the specific student
            List<SPMResult> results = spmDao.getSPMResults(studentId);
            
            // Fetch basic student information to display their name on the page
            Student s = studentDAO.getStudentByMatrix(studentId);
            
            // Set attributes to render the detail page
            request.setAttribute("spmResults", results);
            request.setAttribute("student", s); 
            
            // Forward the request to the detail JSP
            request.getRequestDispatcher("SPMDetail.jsp").forward(request, response);
        }
    }
}