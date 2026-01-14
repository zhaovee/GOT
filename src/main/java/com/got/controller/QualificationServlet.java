package com.got.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.got.dao.ApplicationDAO;
import com.got.model.Application;

@WebServlet("/QualificationCheck")
public class QualificationServlet extends HttpServlet {
    private ApplicationDAO dao = new ApplicationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage", "Qualification");
        request.setAttribute("appList", dao.getAllApplications());
        request.getRequestDispatcher("QualificationList.jsp").forward(request, response);
    }
}