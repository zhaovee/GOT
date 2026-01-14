package com.got.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.got.dao.AdvisorDAO;
import com.got.dao.StudentDAO;
import com.got.model.Advisor;
import com.got.model.Student;

@WebServlet("/StudentList")
public class StudentListServlet extends HttpServlet {
    
    // Initialize Data Access Objects (DAOs) for database operations
    private StudentDAO studentDAO = new StudentDAO();
    private AdvisorDAO advisorDAO = new AdvisorDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve Current Session and Advisor
        // Get the current session to check for a logged-in user
        HttpSession session = request.getSession();
        Advisor currentAdvisor = (Advisor) session.getAttribute("user");

        // 2. Handle Search Logic
        // Get the search keyword from the request parameter
        String searchName = request.getParameter("search");
        List<Student> displayList;

        if (searchName != null && !searchName.trim().isEmpty()) {
            // If a search term is provided, use the DAO to perform a fuzzy search in the database
            displayList = studentDAO.searchStudents(searchName);
        } else {
            // If no search term is provided, fetch the list of all students
            displayList = studentDAO.getAllStudents();
        }

        // 3. Prepare Data for View
        // Set attributes to be accessed by the JSP page
        request.setAttribute("advisor", currentAdvisor);
        request.setAttribute("studentList", displayList);
        
        // Set the active page identifier to highlight the "Student List" item in the sidebar
        request.setAttribute("activePage", "StudentList"); 

        // 4. Render the Page
        // Forward the request to the StudentList.jsp page for display
        request.getRequestDispatcher("StudentList.jsp").forward(request, response);
    }
}