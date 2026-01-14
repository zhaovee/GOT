<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" href="css/main.css">
    <style>
        body { background-color: #EEF2F6; justify-content: center; align-items: center; }
        .card { width: 400px; margin: auto; text-align: center; }
    </style>
</head>
<body>
    <div class="card">
        <h3>Reset Password</h3>
        <form action="ResetPassword" method="post">
            <input type="text" name="username" placeholder="Enter Username" class="input-field" style="width:100%; margin-bottom:10px;" required>
            <input type="password" name="newPass" placeholder="New Password" class="input-field" style="width:100%; margin-bottom:10px;" required>
            <input type="password" name="confirmPass" placeholder="Confirm New Password" class="input-field" style="width:100%;" required>
            
            <button type="submit" style="background:#722626; color:white; padding:10px 30px; border:none; border-radius:5px; margin-top:20px; cursor:pointer;">Update Password</button>
        </form>
        <p style="color:green;">${message}</p>
        <p style="color:red;">${error}</p>
        <a href="Login.jsp" style="font-size:12px;">Back to Login</a>
    </div>
</body>
</html>