<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // haven't login -> Login.jsp
    // Already login -> StudentList
    if (session.getAttribute("user") == null) {
        response.sendRedirect("Login.jsp");
    } else {
        response.sendRedirect("StudentList");
    }
%>