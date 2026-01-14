<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card" style="min-height: 500px; height: auto; flex-shrink: 0; padding-bottom: 50px;">
    
    <div style="display: flex; justify-content: center; margin-bottom: 30px;">
        <div class="search-box" style="width: 400px; display: flex; align-items: center; background: #E0E0E0; border: none;">
            <i class="fas fa-search" style="color: #666; margin-right: 10px;"></i>
            <input type="text" placeholder="Name/IC./ISID/Matric No." style="border: none; background: transparent; width: 100%; outline: none;">
        </div>
    </div>

    <c:forEach var="stu" items="${studentList}">
        <a href="${targetUrl}?studentId=${stu.matrixNo}" style="text-decoration: none; color: inherit; display: block;">
            <div style="background-color: #E0E0E0; padding: 15px 30px; border-radius: 8px; display: flex; align-items: center; margin: 0 auto 15px auto; max-width: 800px; cursor: pointer; transition: 0.2s;">
                <div class="avatar-circle" style="width: 40px; height: 40px; font-size: 18px; margin-right: 20px; background-color: #d1c4e9; color: #5e35b1;">
                    <i class="fas fa-user"></i>
                </div>
                <span style="font-weight: bold; font-size: 16px;">${stu.name} &nbsp;&nbsp;
                <span style="font-weight: normal; color: #555;">${stu.matrixNo}</span></span>
            </div>
        </a>
    </c:forEach>
    
    <c:if test="${empty studentList}">
        <p style="text-align:center; color:#666;">No students found.</p>
    </c:if>
</div>