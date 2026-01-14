<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Detail - GOT</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <jsp:include page="includes/sidebar.jsp" />

    <div class="main-content">
        <c:set var="pageTitle" value="Basic Information" scope="request"/>
        <c:set var="breadcrumb" value="Student Detail" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <c:choose>
                <c:when test="${not empty student}">
                    <div class="profile-container" style="align-items: flex-start;">
                        <div class="avatar-circle" style="width: 120px; height: 120px;">
                            <i class="fas fa-user" style="font-size: 50px;"></i>
                        </div>
                        
                        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 40px; width: 100%;">
                            <div class="info-grid">
                                <span class="label">Name :</span> <span>${student.name}</span>
                                <span class="label">Program :</span> <span>${student.program}</span>
                                <span class="label">Religion :</span> <span>${student.religion}</span>
                                <span class="label">Email :</span> <span>${student.email}</span>
                            </div>

                            <div class="info-grid">
                                <span class="label">MatrixNo :</span> <span>${student.matrixNo}</span>
                                <span class="label">Gender :</span> <span>${student.gender}</span>
                                <span class="label">Nationality :</span> <span>${student.nationality}</span>
                                <span class="label">Phone :</span> <span>${student.phone}</span>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h3>Student not found.</h3>
                    <a href="StudentList">Back to List</a>
                </c:otherwise>
            </c:choose>
        </div>
        
        <div style="margin-left: 30px;">
             <a href="StudentList" style="color: #722626; text-decoration: none;">&larr; Back to List</a>
        </div>
    </div>
</body>
</html>