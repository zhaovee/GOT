<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Selection - Detail</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .search-area { text-align: center; margin-bottom: 20px; position: relative; }
        .search-input { 
            padding: 10px 20px; width: 300px; border-radius: 20px; border: 1px solid #ccc; 
            background: #E0E0E0; outline: none; padding-left: 40px;
        }
        .search-icon-inside { position: absolute; left: calc(48% - 135px); top: 10px; color: #666; }
        
        /* Search Result Pop-out */
        .search-results-box {
            background-color: #E0E0E0;
            width: 350px;
            margin: 10px auto;
            border-radius: 10px;
            padding: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .result-item {
            padding: 10px;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
            text-align: left;
            font-size: 13px;
            display: flex; justify-content: space-between;
        }
        .result-item:last-child { border-bottom: none; }
        .result-item:hover { background-color: #d0d0d0; }
        .btn-add { color: #2e7d32; font-weight: bold; text-decoration: none; }

        /* Selected Course List */
        .selected-list { margin-top: 40px; }
        .selected-label { font-size: 14px; font-weight: bold; margin-bottom: 10px; }
        .selected-item {
            background-color: #D9D9D9;
            padding: 15px 20px;
            margin-bottom: 10px;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 14px;
            font-weight: bold;
        }
        .btn-remove { color: #999; cursor: pointer; text-decoration: none; font-size: 16px;}
        .btn-remove:hover { color: red; }

        .summary-info { margin-top: 40px; font-size: 14px; font-weight: bold; line-height: 2; }
    </style>
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="Course Selection" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card" style="min-height: 500px; height: auto; flex-shrink: 0; padding-bottom: 50px;">
            
            <div style="display: flex; align-items: center; margin-bottom: 40px;">
                <span style="font-size: 14px; font-weight: bold; margin-right: 20px;">Now Operating On:</span>
                <div class="avatar-circle" style="width: 30px; height: 30px; font-size: 14px; margin-right: 10px; background-color: #d1c4e9; color: #5e35b1;">
                    <i class="fas fa-user"></i>
                </div>
                <span style="font-weight: bold; font-size: 14px;">${student.name} &nbsp; <span style="color:#666;">${student.matrixNo}</span></span>
            </div>

            <div class="search-area">
                <form action="CourseSelection" method="get">
                    <input type="hidden" name="studentId" value="${student.matrixNo}">
                    <input type="hidden" name="action" value="search">
                    <i class="fas fa-search search-icon-inside"></i>
                    <input type="text" name="query" class="search-input" placeholder="Search Course Code/Name" value="${searchQuery}" autocomplete="off">
                </form>

                <c:if test="${not empty searchResults}">
                    <div class="search-results-box">
                        <c:forEach var="res" items="${searchResults}">
                            <div class="result-item">
                                <span><b>${res.code}</b> - ${res.name} (${res.credit} cr)</span>
                                <a href="CourseSelection?studentId=${student.matrixNo}&action=add&code=${res.code}" class="btn-add">
                                    <i class="fas fa-plus"></i> Add
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${empty searchResults and not empty searchQuery}">
                     <div class="search-results-box">No course found.</div>
                </c:if>
            </div>

            <div class="selected-list">
                <div class="selected-label">Course Selected:</div>
                
                <c:forEach var="sel" items="${selectedCourses}">
                    <div class="selected-item">
                        <span>${sel.code} - ${sel.name}</span>
                        <a href="CourseSelection?studentId=${student.matrixNo}&action=remove&code=${sel.code}" class="btn-remove">
                            <i class="fas fa-times"></i>
                        </a>
                    </div>
                </c:forEach>
                
                <c:if test="${empty selectedCourses}">
                    <div style="text-align: center; color: #999; margin: 20px;">No courses selected yet.</div>
                </c:if>
            </div>

            <div class="summary-info">
                <div>Total Credits of Selected Courses: &nbsp; <span style="font-weight: normal;">${selectedCredits}</span></div>
                <div>Total Credits of All Courses: &nbsp; <span style="font-weight: normal;">${totalCredits}/90</span></div>
            </div>

            <form action="CourseSelection" method="post" style="text-align: center; margin-top: 40px;">
                <input type="hidden" name="studentId" value="${student.matrixNo}">
                <button type="submit" style="background: #111; color: white; padding: 12px 60px; border: none; border-radius: 5px; cursor: pointer;">Submit</button>
            </form>
            
            <c:if test="${not empty message}">
                <div style="color: green; text-align: center; margin-top: 15px;">${message}</div>
            </c:if>

        </div>
    </div>
</body>
</html>