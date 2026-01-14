<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student List - GOT</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <jsp:include page="includes/sidebar.jsp" />

    <div class="main-content">
        <c:set var="pageTitle" value="Academic Advisor" scope="request"/>
        <c:set var="breadcrumb" value="List of Students" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <div class="profile-container">
                <div class="avatar-circle">
                    <i class="fas fa-user"></i>
                </div>
                <div class="info-grid">
                    <span class="label">Name :</span> <span>${advisor.name}</span>
                    <span class="label">Phone :</span> <span>${advisor.phone}</span>
                    <span class="label">Email :</span> <span>${advisor.email}</span>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="table-header">
                <h3>List of Students (Semester)</h3>
                <form action="StudentList" method="get">
                    <input type="text" name="search" class="search-box" placeholder="Search name..." value="${param.search}">
                    <button type="submit" style="display:none;"></button>
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Year/COURSE</th>
                        <th>GPA</th>
                        <th>ACTIVE CODE</th>
                        <th>DETAIL</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="stu" items="${studentList}">
                        <tr>
                            <td>${stu.name}</td>
                            <td>${stu.currentYear}/${stu.program}</td>
                            <td>${stu.gpa}</td>
                            <td>${stu.activeCode}</td>
                            <td>
                                <a href="StudentDetail?matrixNo=${stu.matrixNo}" class="btn-detail">
                                    <i class="fas fa-search"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty studentList}">
                        <tr><td colspan="5" style="text-align:center;">No students found.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>