<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar">
    <div class="sidebar-header">GOT Monitoring System</div>
    
    <a href="StudentList" class="nav-item ${activePage == 'StudentList' ? 'active' : ''}">
        <span class="nav-icon">👤</span> Student List
    </a>
    <a href="GPACalculator" class="nav-item ${activePage == 'GPACalculator' ? 'active' : ''}">
        <span class="nav-icon">➗</span> GPA Calculator
    </a>
    <a href="AcademicProgress" class="nav-item ${activePage == 'AcademicProgress' ? 'active' : ''}">
        <span class="nav-icon">📖</span> Academic Progress
    </a>
    <a href="QualificationCheck" class="nav-item ${activePage == 'QualificationCheck' ? 'active' : ''}">
        <span class="nav-icon">🎓</span> Qualification Check
    </a>
    <a href="SPMCheck" class="nav-item ${activePage == 'SPMCheck' ? 'active' : ''}">
        <span class="nav-icon">📝</span> SPM Result
    </a>
    <a href="ViewFinancialStatus" class="nav-item ${activePage == 'ViewFinancialStatus' ? 'active' : ''}">
        <span class="nav-icon">💳</span> View Financial Status
    </a>
    <a href="CourseSelection" class="nav-item ${activePage == 'CourseSelection' ? 'active' : ''}">
        <span class="nav-icon">🔖</span> Course Selection
    </a>
    
    <div style="margin-top: auto; border-top: 1px solid #eee;">
         <a href="Logout" class="nav-item">
            <span class="nav-icon">🚪</span> Logout
        </a>
    </div>
</div>