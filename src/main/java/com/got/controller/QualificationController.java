package com.got.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.ApplicationDAO;
import com.got.dao.CourseDAO;
import com.got.dao.FinanceDAO;
import com.got.model.Application;

@Controller
public class QualificationController {

    // Inject necessary DAOs using Spring Dependency Injection
    @Autowired
    private ApplicationDAO appDAO;
    
    @Autowired
    private FinanceDAO financeDAO;
    
    @Autowired
    private CourseDAO courseDAO;

    // 1. Handle Qualification List View (Merged from QualificationServlet)
    // Maps to the URL /QualificationCheck for GET requests
    @GetMapping("/QualificationCheck")
    public String viewQualificationList(Model model) {
        // Set the active page for sidebar highlighting
        model.addAttribute("activePage", "Qualification");
        
        // Fetch all applications and add them to the model
        model.addAttribute("appList", appDAO.getAllApplications());
        
        // Return the view name which resolves to QualificationList.jsp
        return "QualificationList";
    }

    // 2. Handle Qualification Detail View (Merged from QualificationDetailServlet)
    // Maps to the URL /QualificationDetail for GET requests
    @GetMapping("/QualificationDetail")
    public String viewQualificationDetail(@RequestParam("appId") int appId, Model model) {
        // Retrieve Application Information using the provided ID
        Application app = appDAO.getApplicationById(appId);
        
        if (app != null) {
            String matrixNo = app.getStudentMatrix();

            // Retrieve Financial Status (Debt)
            double debt = financeDAO.getTotalDebt(matrixNo);

            // Retrieve Academic Status (Credits)
            int credits = courseDAO.getTotalCreditsEarned(matrixNo);

            // Pass data to the view
            model.addAttribute("app", app);
            model.addAttribute("debt", debt);
            model.addAttribute("credits", credits);
        }
        
        // Set active page for sidebar
        model.addAttribute("activePage", "Qualification");
        
        // Return the view name which resolves to QualificationDetail.jsp
        return "QualificationDetail";
    }

    // 3. Handle Qualification Action (Merged from QualificationActionServlet)
    // Maps to the URL /QualificationAction for POST requests (Approve/Reject)
    @PostMapping("/QualificationAction")
    public String processQualificationAction(
            @RequestParam("appId") int appId,
            @RequestParam("action") String action,
            @RequestParam("matrixNo") String matrixNo) {
        
        // Construct the notification message
        String notifMsg = "Your qualification application (ID: " + appId + ") has been " + action + ".";
        
        // Update Database and insert notification
        appDAO.processApplication(appId, action, matrixNo, notifMsg);
        
        // Redirect back to the list page after processing
        return "redirect:/QualificationCheck";
    }
}