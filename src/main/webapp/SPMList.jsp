<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>SPM Check</title>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="includes/sidebar.jsp" />
    <div class="main-content">
        <c:set var="pageTitle" value="Intake Qualification (SPM)" scope="request"/>
        <jsp:include page="includes/header.jsp" />
        <jsp:include page="includes/student_selector.jsp" />
    </div>
</body>
</html>