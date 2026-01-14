package com.got.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.got.dao.AdvisorDAO;

@WebServlet("/ResetPassword")
public class ResetPasswordServlet extends HttpServlet {
    
    private AdvisorDAO advisorDAO = new AdvisorDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Retrieve Input Data
        // Get the username and the two password fields from the request parameters
        String u = request.getParameter("username");
        String p1 = request.getParameter("newPass");
        String p2 = request.getParameter("confirmPass");
        
        // 2. Validate Password Confirmation
        // Check if the new password matches the confirmation password
        if (!p1.equals(p2)) {
            // If they don't match, set an error message
            request.setAttribute("error", "Passwords do not match!");
            // Forward back to the reset password page to show the error
            request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
            return; // Stop execution here
        }

        // 3. Update Password in Database
        // Call the DAO to update the password for the given username
        boolean success = advisorDAO.updatePassword(u, p1);
        
        // 4. Handle Update Result
        if (success) {
            // If update was successful, set a success message
            request.setAttribute("message", "Password updated! Please login.");
        } else {
            // If update failed (e.g., username not found), set an error message
            request.setAttribute("error", "User not found.");
        }
        
        // 5. Render Response
        // Forward the request back to the JSP to display the result message
        request.getRequestDispatcher("ResetPassword.jsp").forward(request, response);
    }
}