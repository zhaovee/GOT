package com.got.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    // Pre-handle request to check authentication status
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the user is already logged in (session exists and has "user" attribute)
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            return true; // Allow the request to proceed
        } else {
            // Redirect to the login page if not authenticated
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return false; // Stop execution
        }
    }
}
