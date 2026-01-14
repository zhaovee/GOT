package com.got.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.FinanceDAO;
import com.got.dao.StudentDAO;
import com.got.model.FinancialRecord;
import com.got.model.Student;

@Controller
public class ViewFinancialStatusController {
    
    // Inject Data Access Objects
    @Autowired
    private StudentDAO studentDAO;
    
    @Autowired
    private FinanceDAO financeDAO;

    // Handle GET request for Financial Status
    @GetMapping("/ViewFinancialStatus")
    public String viewFinancialStatus(
            @RequestParam(value = "studentId", required = false) String studentId, 
            Model model) {
        
        // Set the active page identifier for sidebar highlighting
        model.addAttribute("activePage", "ViewFinancialStatus");

        if (studentId == null) {
            // Case 1: No Student Selected (List View)
            List<Student> list = studentDAO.getAllStudents();
            
            // Set attributes for the view
            model.addAttribute("studentList", list);
            model.addAttribute("targetUrl", "ViewFinancialStatus"); 
            
            // Return view name for the main list JSP page
            return "ViewFinancialStatus"; // Resolves to ViewFinancialStatus.jsp
        } else {
            // Case 2: Student Selected (Detail View)
            Student target = studentDAO.getStudentByMatrix(studentId);
            
            if (target != null) {
                // Fetch financial records (This returns ALL records including PAID ones, which is correct for history)
                List<FinancialRecord> records = financeDAO.getRecordsByStudent(studentId);
                
                // FIX: Use the DAO method that specifically filters for 'UNPAID' status.
                // Previously, the manual loop incorrectly summed all records regardless of status.
                double totalAmount = financeDAO.getTotalDebt(studentId);

                // Set attributes for the detail page
                model.addAttribute("student", target);
                model.addAttribute("records", records);
                model.addAttribute("totalAmount", totalAmount);
                
                // Return view name for the detail JSP page
                return "ViewFinancialStatusDetail"; // Resolves to ViewFinancialStatusDetail.jsp
            } else {
                // If the student ID is invalid or not found, redirect back to the list view
                return "redirect:/ViewFinancialStatus";
            }
        }
    }
}