<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Application Detail</title>
    <link rel="stylesheet" href="css/main.css">
    <style>
        .check-item { margin: 15px 0; padding: 15px; border-radius: 8px; font-weight: bold; display: flex; align-items: center; gap: 10px;}
        .check-pass { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .check-fail { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        
        .action-btns { margin-top: 30px; text-align: center; }
        .btn-act { padding: 12px 40px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; margin: 0 10px;}
        .btn-approve { background-color: #28a745; color: white; }
        .btn-reject { background-color: #dc3545; color: white; }
        .btn-disabled { background-color: #ccc; color: #666; cursor: not-allowed; }
    </style>
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="Application Assessment" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <h2>${app.type} Application</h2>
            <p>Applicant: <b>${app.studentName}</b> (${app.studentMatrix})</p>

            <!-- Finance Check Logic -->
            <c:choose>
                <c:when test="${debt <= 0}">
                    <div class="check-item check-pass">
                        <span>✔</span> Finance Status: No debt exists.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="check-item check-fail">
                        <span>✖</span> Finance Status: Outstanding Debt of RM ${debt}
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Academic Check Logic -->
            <c:choose>
                <c:when test="${credits >= 90}">
                    <div class="check-item check-pass">
                        <span>✔</span> Academic Status: Enough credits (${credits}/90).
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="check-item check-fail">
                        <span>✖</span> Academic Status: Insufficient credits (${credits}/90).
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Action Buttons -->
            <div class="action-btns">
                <c:if test="${app.status == 'PENDING'}">
                    <form action="QualificationAction" method="post">
                        <input type="hidden" name="appId" value="${app.appId}">
                        <input type="hidden" name="matrixNo" value="${app.studentMatrix}">
                        
                        <!-- Button will be activated only when both available -->
                        <c:choose>
                            <c:when test="${debt <= 0 && credits >= 90}">
                                <button name="action" value="APPROVED" class="btn-act btn-approve">Approve</button>
                                <button name="action" value="REJECTED" class="btn-act btn-reject">Reject</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn-act btn-disabled" disabled>Approve</button>
                                <button type="button" class="btn-act btn-disabled" disabled>Reject</button>
                                <p style="font-size: 12px; color: red; margin-top: 10px;">Cannot approve due to status issues.</p>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </c:if>
                <c:if test="${app.status != 'PENDING'}">
                    <h3>This application has been ${app.status}.</h3>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>