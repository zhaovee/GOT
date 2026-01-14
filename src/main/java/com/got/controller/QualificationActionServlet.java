package com.got.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.got.dao.ApplicationDAO;

@WebServlet("/QualificationAction")
public class QualificationActionServlet extends HttpServlet {
    private ApplicationDAO dao = new ApplicationDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int appId = Integer.parseInt(request.getParameter("appId"));
        String action = request.getParameter("action"); // APPROVED or REJECTED
        String matrixNo = request.getParameter("matrixNo");
        
        String notifMsg = "Your qualification application (ID: " + appId + ") has been " + action + ".";
        
        // Update Database and insert notification
        dao.processApplication(appId, action, matrixNo, notifMsg);
        
        response.sendRedirect("QualificationCheck");
    }
}
