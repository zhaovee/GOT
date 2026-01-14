package com.got.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        // Initialization code if needed
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get the current request URI
        String uri = req.getRequestURI();
        
        // Define public resources that do not require login (White List)
        // 1. Login page and Login Servlet processing
        // 2. Static resources like CSS
        // 3. Reset Password page and logic
        boolean isLoginJsp = uri.endsWith("Login.jsp");
        boolean isLoginServlet = uri.endsWith("Login");
        boolean isResetJsp = uri.endsWith("ResetPassword.jsp");
        boolean isResetServlet = uri.endsWith("ResetPassword");
        boolean isCss = uri.contains("/css/"); 
        
        // Check if the user is already logged in (session exists and has "user" attribute)
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Logic:
        // 1. If user is logged in, allow access.
        // 2. If user is accessing a public page (Login/Reset/CSS), allow access.
        // 3. Otherwise, block and redirect to Login page.
        if (isLoggedIn || isLoginJsp || isLoginServlet || isResetJsp || isResetServlet || isCss) {
            // Pass the request along the filter chain
            chain.doFilter(request, response);
        } else {
            // Redirect to the login page
            res.sendRedirect(req.getContextPath() + "/Login.jsp");
        }
    }

    public void destroy() {
        // Cleanup code if needed
    }
}
