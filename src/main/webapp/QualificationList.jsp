<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Track Qualification</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .status-badge { padding: 5px 10px; border-radius: 15px; font-size: 12px; font-weight: bold; }
        .st-PENDING { background-color: #fff3cd; color: #856404; }
        .st-APPROVED { background-color: #d4edda; color: #155724; }
        .st-REJECTED { background-color: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="Track Qualification" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <h3>Application History</h3>
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Student Name</th>
                        <th>Type</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="app" items="${appList}">
                        <tr>
                            <td><fmt:formatDate value="${app.applyDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${app.studentName} <br><small style="color:#888;">${app.studentMatrix}</small></td>
                            <td>${app.type}</td>
                            <td><span class="status-badge st-${app.status}">${app.status}</span></td>
                            <td>
                                <a href="QualificationDetail?appId=${app.appId}" class="btn-detail">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>