package com.got.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.SPMDAO;
import com.got.dao.StudentDAO;
import com.got.model.SPMResult;
import com.got.model.Student;

@Controller
public class SPMController {
    
    // Inject Data Access Objects
    @Autowired
    private SPMDAO spmDao;
    
    @Autowired
    private StudentDAO studentDAO; 

    // Handle GET request for SPM Check
    @GetMapping("/SPMCheck")
    public String viewSPMCheck(
            @RequestParam(value = "studentId", required = false) String studentId, 
            Model model) {
        
        // Set the active page for sidebar highlighting
        model.addAttribute("activePage", "SPMCheck");

        if (studentId == null) {
            // Case 1: No student selected (Display List Mode)
            // Fetch the list of all students
            List<Student> students = studentDAO.getAllStudents();
            
            // Set attributes for the view
            model.addAttribute("studentList", students);
            model.addAttribute("targetUrl", "SPMCheck");
            
            // Return view name for the student list JSP
            return "SPMList"; // Resolves to SPMList.jsp
        } else {
            // Case 2: A student is selected (Display Detail Mode)
            // Fetch the SPM examination results for the specific student
            List<SPMResult> results = spmDao.getSPMResults(studentId);
            
            // Fetch basic student information
            Student s = studentDAO.getStudentByMatrix(studentId);
            
            // Set attributes for the detail view
            model.addAttribute("spmResults", results);
            model.addAttribute("student", s); 
            
            // Return view name for the detail JSP
            return "SPMDetail"; // Resolves to SPMDetail.jsp
        }
    }
}