<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Academic Progress - Detail</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .legend { font-size: 12px; margin-bottom: 20px; text-align: right; }
        .legend span { margin-left: 10px; display: inline-flex; align-items: center; }
        .legend-box { width: 12px; height: 12px; display: inline-block; margin-right: 5px; border-radius: 2px; }
        .box-fail { background-color: #ffcccc; border: 1px solid #ff9999; }
        .box-retake { background-color: #fff3cd; border: 1px solid #ffeeba; }
        .box-pass { background-color: #d4edda; border: 1px solid #c3e6cb; }
		.box-pending { background-color: #f0f0f0; border: 1px solid #ccc; }

        .semester-row { display: flex; align-items: flex-start; margin-bottom: 25px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        .sem-name { width: 150px; font-weight: bold; font-size: 14px; padding-top: 10px; }
        .courses-grid { flex: 1; display: flex; flex-wrap: wrap; gap: 15px; }
        
        .course-item { 
            display: flex; align-items: center; 
            padding: 8px 12px; border-radius: 6px; 
            min-width: 220px;
            border: 1px solid #ddd;
        }
        
        /* change color based on status */
        .status-FAIL { background-color: #ffe6e6; border-color: #ffadad; }
        .status-RETAKE { background-color: #fff9db; border-color: #ffe066; }
        .status-PASS { background-color: #e3f9e5; border-color: #a3cfbb; }
		.status-PENDING { background-color: #f0f0f0; border-color: #ccc; color: #888; }
		
        .course-code { font-weight: bold; margin-right: 10px; font-size: 14px; }
        .status-select { 
            border: none; background: transparent; font-weight: bold; 
            cursor: pointer; outline: none; font-size: 13px; width: 80px;
        }

        .summary-section { margin-top: 30px; font-size: 14px; font-weight: bold; }
        .eval-box { width: 100%; height: 100px; padding: 10px; margin-top: 10px; border-radius: 8px; border: 1px solid #ccc; resize: none; font-family: inherit; }
        .btn-submit { background-color: #111; color: white; padding: 10px 40px; border-radius: 5px; border: none; cursor: pointer; float: right; margin-top: 10px; }
    </style>
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="Academic Progress" scope="request"/>
        <c:set var="breadcrumb" value="Student Detail" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card">
            <div style="display: flex; justify-content: space-between; margin-bottom: 30px;">
                <div>
                    <strong>Now Operating On:</strong> &nbsp;&nbsp; ${targetStudent.name} &nbsp; <span style="color:#666;">${targetStudent.matrixNo}</span>
                </div>
                <div class="legend">
                    Legend: 
                    <span><div class="legend-box box-fail"></div> FAIL</span>
                    <span><div class="legend-box box-retake"></div> RETAKE</span>
                    <span><div class="legend-box box-pass"></div> PASS</span>
                    <span><div class="legend-box box-pending"></div> PENDING</span>
                </div>
            </div>

            <p style="font-size: 14px; color: #555;">Course Status (Pass/Fail):</p>

            <form action="AcademicProgress" method="post">
                <input type="hidden" name="studentId" value="${targetStudent.matrixNo}">
                <c:forEach var="sem" items="${semesters}" varStatus="semStatus">
                    <div class="semester-row">
                        <div class="sem-name">${sem.name}</div>
                        <div class="courses-grid">
                            <c:forEach var="course" items="${sem.courses}" varStatus="courseStatus">
                                <div class="course-item status-${course.status}">
                                    <span class="course-code">${course.code}</span>
                                    <i class="fas fa-chevron-right" style="font-size: 10px; margin-right: 10px; opacity: 0.5;"></i>
                                    
                                    <select name="status_${semStatus.index}_${courseStatus.index}" class="status-select">
                                    	<c:if test="${course.status == 'PENDING'}">
        									<option value="PENDING" selected disabled hidden>PENDING</option>
    									</c:if>
                                        <option value="PASS" ${course.status == 'PASS' ? 'selected' : ''}>PASS</option>
                                        <option value="FAIL" ${course.status == 'FAIL' ? 'selected' : ''}>FAIL</option>
                                        <option value="RETAKE" ${course.status == 'RETAKE' ? 'selected' : ''}>RETAKE</option>
                                    </select>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>

                <div class="summary-section">
                    <p>Credit Earned this Semester: <span style="font-weight: normal;">${currentSemCredits}</span></p>
                    <p>Total Credits: <span style="font-weight: normal;">${totalCredits}/90</span></p>
                    
                    <label>Evaluations:</label>
                    <textarea name="evaluation" class="eval-box" placeholder="Anything to tell student...">${savedEvaluation}</textarea>
                </div>

                <button type="submit" class="btn-submit">Submit</button>
                <div style="clear: both;"></div>
            </form>
            
            <c:if test="${not empty message}">
                <div style="color: green; text-align: center; margin-top: 10px;">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>