<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Financial Status - Detail</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .profile-header-extension {
            background-color: #722626;
            color: white;
            padding: 0 40px 40px 40px;
            display: flex;
            justify-content: flex-end; 
            align-items: center;
            margin-top: -1px; 
        }
        .header-profile-info {
            display: flex;
            align-items: center;
            gap: 20px;
            text-align: left;
        }
        .header-avatar {
            width: 80px; height: 80px;
            border-radius: 50%;
            background-color: #d9d9d9;
            color: #333;
            display: flex; align-items: center; justify-content: center;
            font-size: 35px;
            border: 2px solid #555;
        }
        
        /* status style */
        .status-alert-box {
            text-align: right;
            margin-bottom: 20px;
            padding-bottom: 20px;
            border-bottom: 2px solid #ddd;
            display: flex;
            flex-direction: column;
            align-items: flex-end;
        }
        .status-title {
            font-size: 20px; font-weight: bold; color: #d32f2f; margin-bottom: 10px;
        }
        .status-amount-box {
            background-color: #e0e0e0;
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 24px;
            font-weight: bold;
            color: #d32f2f; 
            display: flex;
            align-items: center;
            gap: 10px;
            width: fit-content;
        }
        
        /* Green Variant */
        .status-green .status-title { color: #2e7d32; }
        .status-green .status-amount-box { color: #2e7d32; }

        /* Table */
        .finance-table { width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 20px; }
        .finance-table th { background-color: #f5f5f5; padding: 15px; font-size: 12px; font-weight: bold; text-align: left; border-bottom: 1px solid #ddd;}
        .finance-table td { background-color: #e0e0e0; padding: 15px; border-bottom: 5px solid white; font-size: 13px; font-weight: bold;}
        .finance-table tr:last-child td { border-bottom: none; }
        
        /* Layout tweak */
        .finance-container { margin-top: -30px; position: relative; z-index: 10; }
    </style>
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="View Financial Status" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="profile-header-extension">
            <div class="header-profile-info">
                <div class="header-avatar"><i class="fas fa-user"></i></div>
                <div style="font-size: 14px; line-height: 1.6;">
                    <div>Name : ${student.name}</div>
                    <div>MatrixNo : ${student.matrixNo}</div>
                    <div>Program : ${student.program}</div>
                    <div>Email : ${student.email}</div>
                </div>
            </div>
        </div>

        <div class="card finance-container">
            
            <c:choose>
                <c:when test="${totalAmount > 0}">
                    <div class="status-alert-box">
                        <div class="status-title">Outstanding Balance of</div>
                        <div class="status-amount-box">
                            <i class="fas fa-exclamation-triangle"></i>
                            <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="" minFractionDigits="2"/> RM
                        </div>
                    </div>
                    <div style="text-align: center; font-size: 20px; font-weight: bold; margin-bottom: 20px;">Unpaid Item Debt</div>
                </c:when>
                <c:otherwise>
                    <div class="status-alert-box status-green">
                        <div class="status-title">Account Balance</div>
                        <div class="status-amount-box">
                            <i class="fas fa-check-circle"></i>
                            0.00 RM </div>
                    </div>
                    <div style="text-align: center; font-size: 20px; font-weight: bold; margin-bottom: 20px; color: #555;">No Outstanding Debt</div>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty records}">
                <table class="finance-table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Describe</th>
                            <th style="text-align: right;">Amount (RM)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rec" items="${records}">
                            <tr>
                                <td>${rec.name}</td>
                                <td>${rec.date}</td>
                                <td>${rec.description}</td>
                                <td style="text-align: right;">
                                    <fmt:formatNumber value="${rec.amount}" minFractionDigits="2"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <div style="text-align: center; margin-top: 20px; color: #333; font-size: 12px;">
                <i class="fas fa-chevron-left"></i> &nbsp; 1 &nbsp; <i class="fas fa-chevron-right"></i>
            </div>
           
        </div>
    </div>
</body>
</html>