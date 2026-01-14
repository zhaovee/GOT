package com.got.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.StudentDAO;
import com.got.model.Advisor;
import com.got.model.Student;

@Controller
public class StudentListController {
    
    // Inject StudentDAO to handle database operations for students
    @Autowired
    private StudentDAO studentDAO;

    // Handle GET requests to view the student list
    @GetMapping("/StudentList")
    public String getStudentList(
            @RequestParam(value = "search", required = false) String searchName,
            HttpSession session, 
            Model model) {
        
        // 1. Retrieve Current Session and Advisor
        // Get the logged-in advisor from the session for display purposes
        Advisor currentAdvisor = (Advisor) session.getAttribute("user");

        // 2. Handle Search Logic
        List<Student> displayList;
        if (searchName != null && !searchName.trim().isEmpty()) {
            // If a search term is provided, use the DAO to perform a fuzzy search
            displayList = studentDAO.searchStudents(searchName);
        } else {
            // If no search term is provided, fetch the list of all students
            displayList = studentDAO.getAllStudents();
        }

        // 3. Prepare Data for View
        // Add attributes to the model to be accessed by the JSP page
        model.addAttribute("advisor", currentAdvisor);
        model.addAttribute("studentList", displayList);
        
        // Set the active page identifier to highlight the "Student List" item in the sidebar
        model.addAttribute("activePage", "StudentList"); 

        // 4. Render the Page
        // Return the view name which resolves to StudentList.jsp
        return "StudentList";
    }
}