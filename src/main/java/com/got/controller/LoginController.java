package com.got.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.AdvisorDAO;
import com.got.model.Advisor;

@Controller
public class LoginController {
    
    // Inject AdvisorDAO to handle database operations for advisors
    @Autowired
    private AdvisorDAO advisorDAO;

    // Optional: Explicitly map the login page view if strictly needed, 
    // though the interceptor usually redirects here.
    @GetMapping("/Login")
    public String viewLoginPage() {
        return "Login"; // Resolves to Login.jsp
    }

    // Handle login form submission via POST request
    @PostMapping("/Login")
    public String login(@RequestParam("username") String u, 
                        @RequestParam("password") String p, 
                        HttpSession session, 
                        Model model) {
        
        // 1. Authenticate
        // Verify credentials using the DAO method
        Advisor advisor = advisorDAO.login(u, p);
        
        if (advisor != null) {
            // 2. Login Successful
            // Store the logged-in advisor object in the session
            session.setAttribute("user", advisor);
            
            // Redirect the user to the main dashboard (Student List)
            return "redirect:/StudentList"; 
        } else {
            // 3. Login Failed
            // Set an error message to display on the login page
            model.addAttribute("error", "Invalid Credentials");
            
            // Return the view name to re-render the login page with the error
            return "Login"; 
        }
    }
}