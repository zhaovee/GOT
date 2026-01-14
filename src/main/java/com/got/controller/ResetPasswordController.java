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
public class ResetPasswordController {

    @Autowired
    private AdvisorDAO advisorDAO;
    
    // Optional: Handler for viewing the page via GET (if not accessed purely by forward)
    @GetMapping("/ResetPassword")
    public String showResetPage() {
        return "ResetPassword";
    }

    @PostMapping("/ResetPassword")
    public String resetPassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            Model model) {
        
        // 1. Get Current User
        Advisor advisor = (Advisor) session.getAttribute("user");
        
        // Security check: Ensure user is logged in
        if (advisor == null) {
            return "redirect:/Login";
        }

        // 2. Validate Inputs
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "New passwords do not match.");
            model.addAttribute("status", "error");
            return "ResetPassword";
        }

        // 3. Verify Old Password
        // We use the login method or a check logic to verify the current password
        Advisor validAdvisor = advisorDAO.login(advisor.getUsername(), currentPassword);
        
        if (validAdvisor != null) {
            // 4. Update Password
            boolean success = advisorDAO.updatePassword(advisor.getUsername(), newPassword);
            
            if (success) {
                model.addAttribute("message", "Password updated successfully.");
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("message", "Database error occurred.");
                model.addAttribute("status", "error");
            }
        } else {
            model.addAttribute("message", "Current password is incorrect.");
            model.addAttribute("status", "error");
        }

        return "ResetPassword";
    }
}