<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - GOT Monitoring System</title>
    <link rel="stylesheet" href="css/main.css">
    <style>
        body { background-color: #722626; justify-content: center; align-items: center; }
        .login-card { background: white; padding: 40px; border-radius: 10px; width: 350px; text-align: center; }
        .login-btn { width: 100%; background: #111; color: white; padding: 10px; border: none; border-radius: 5px; cursor: pointer; margin-top: 20px;}
        .input-field { margin-bottom: 15px; }
        .link { font-size: 12px; color: #555; text-decoration: none; display: block; margin-top: 15px; }
    </style>
</head>
<body>
    <div class="login-card">
        <h2 style="color: #722626;">GOT Monitoring System</h2>
        <h4 style="color: #555;">Advisor Login</h4>
        <form action="Login" method="post">
            <input type="text" name="username" placeholder="Username" class="input-field" style="width:100%; padding:10px;" required>
            <input type="password" name="password" placeholder="Password" class="input-field" style="width:100%; padding:10px;" required>
            <button type="submit" class="login-btn">LOGIN</button>
        </form>
        <a href="ResetPassword.jsp" class="link">Forgot Password?</a>
        <p style="color:red; font-size:12px;">${error}</p>
    </div>
</body>
</html>