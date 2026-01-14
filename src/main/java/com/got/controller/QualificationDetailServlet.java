package com.got.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.ApplicationDAO;
import com.got.dao.CourseDAO;
import com.got.dao.FinanceDAO;
import com.got.model.Application;

@WebServlet("/QualificationDetail")
public class QualificationDetailServlet extends HttpServlet {
    
    // Initialize Data Access Objects (DAOs) for different modules
    private ApplicationDAO appDAO = new ApplicationDAO();
    private FinanceDAO financeDAO = new FinanceDAO();
    private CourseDAO courseDAO = new CourseDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve Application Information
        // Parse the application ID from the URL parameter
        int appId = Integer.parseInt(request.getParameter("appId"));
        // Fetch the full application details from the database using the ID
        Application app = appDAO.getApplicationById(appId);
        
        // Extract the student's matrix number from the application object
        String matrixNo = app.getStudentMatrix();

        // 2. Retrieve Financial Status
        // Use FinanceDAO to calculate the total outstanding debt for this specific student
        double debt = financeDAO.getTotalDebt(matrixNo);

        // 3. Retrieve Academic Status
        // Use CourseDAO to calculate the total credits earned by this student so far
        int credits = courseDAO.getTotalCreditsEarned(matrixNo);

        // 4. Pass Data to the View
        // Set the retrieved data as request attributes to be displayed in the JSP
        request.setAttribute("app", app);
        request.setAttribute("debt", debt);
        request.setAttribute("credits", credits);
        request.setAttribute("activePage", "Qualification"); // Highlight the sidebar menu
        
        // Forward the request to the detail JSP page for rendering
        request.getRequestDispatcher("QualificationDetail.jsp").forward(request, response);
    }
}