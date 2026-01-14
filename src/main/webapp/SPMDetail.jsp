<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>SPM Detail</title>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="SPM Details" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <h3>SPM Results</h3>
            <table style="width: 100%; text-align: left; border-collapse: collapse;">
                <tr style="background: #eee;">
                    <th style="padding:10px;">Subject</th>
                    <th style="padding:10px;">Grade</th>
                    <th style="padding:10px;">Status</th>
                </tr>
                <c:forEach var="res" items="${spmResults}">
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding:10px;">${res.subject}</td>
                        <td style="padding:10px; font-weight: bold;">${res.grade}</td>
                        <td style="padding:10px;">
                            <c:choose>
                                <c:when test="${res.pass}">
                                    <span style="color: green; font-weight: bold;">PASS</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: red; font-weight: bold;">FAIL</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <div style="margin-top: 20px; font-size: 13px; color: #666;">
                * Pass Condition: Grade C or above.
            </div>
            
            <a href="SPMCheck" style="display:inline-block; margin-top:20px; color:#111;">&larr; Back to List</a>
        </div>
    </div>
</body>
</html>