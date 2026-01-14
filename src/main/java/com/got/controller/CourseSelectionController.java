package com.got.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.CourseDAO;
import com.got.dao.StudentDAO;
import com.got.model.AcademicCourse;
import com.got.model.Student;

/**
 * Controller responsible for handling course selection and management
 * for students. This controller supports viewing available students,
 * searching courses, and adding or removing courses for a student.
 */
@Controller
public class CourseSelectionController {
    private static final String CURRENT_SEMESTER = "2025/2026 I";

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private CourseDAO courseDAO;

    @GetMapping("/CourseSelection")
    public String viewCourseSelection(
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "query", required = false) String searchQuery,
            Model model) {

        model.addAttribute("activePage", "CourseSelection");

        if (studentId == null) {
            List<Student> list = studentDAO.getAllStudents();
            model.addAttribute("studentList", list);
            model.addAttribute("targetUrl", "CourseSelection");
            return "CourseSelection";
        } else {
            prepareSelectionData(studentId, searchQuery, model);
            return "CourseSelectionDetail";
        }
    }

    @PostMapping("/CourseSelection")
    public String processCourseAction(
            @RequestParam("studentId") String studentId,
            @RequestParam("action") String action,
            @RequestParam(value = "courseCode", required = false) String courseCode,
            Model model) {

        // Handle course registration (强制添加到当前学期)
        if ("add".equals(action)) {
            if (courseCode != null) {
                courseDAO.addCourseForStudent(studentId, courseCode, CURRENT_SEMESTER);
                model.addAttribute("message", "Course added to " + CURRENT_SEMESTER + " successfully.");
            }
        } 
        // Handle course removal
        else if ("remove".equals(action)) {
            if (courseCode != null) {
                courseDAO.removeCourseForStudent(studentId, courseCode);
                model.addAttribute("message", "Course dropped successfully.");
            }
        }
        // Handle Confirm/Submit action
        else {
            model.addAttribute("message", "Course selection for " + CURRENT_SEMESTER + " updated successfully!");
        }

        model.addAttribute("activePage", "CourseSelection");
        prepareSelectionData(studentId, null, model);

        return "CourseSelectionDetail";
    }

    private void prepareSelectionData(String studentId, String searchQuery, Model model) {
        // Retrieve student information
        Student student = studentDAO.getStudentByMatrix(studentId);

        // Retrieve all registered courses
        List<AcademicCourse> allCourses = courseDAO.getStudentCourses(studentId);

        // Split courses into Current and Previous
        List<AcademicCourse> currentSemCourses = new ArrayList<>();
        List<AcademicCourse> prevSemCourses = new ArrayList<>();
        
        int selectedCredits = 0; // Only count current semester credits

        if (allCourses != null) {
            for (AcademicCourse c : allCourses) {
                // Handle potential null semester
                String sem = c.getSemester() != null ? c.getSemester() : "";
                
                if (CURRENT_SEMESTER.equals(sem)) {
                    currentSemCourses.add(c);
                    selectedCredits += c.getCredit();
                } else {
                    prevSemCourses.add(c);
                }
            }
        }

        // Retrieve total credits earned (historical)
        int totalCredits = courseDAO.getTotalCreditsEarned(studentId);

        // Perform course search
        List<AcademicCourse> searchResults = null;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            searchResults = courseDAO.searchCourses(searchQuery);
        }

        model.addAttribute("student", student);
        model.addAttribute("currentSemCourses", currentSemCourses);
        model.addAttribute("prevSemCourses", prevSemCourses);
        model.addAttribute("currentSemester", CURRENT_SEMESTER);
        
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("selectedCredits", selectedCredits); // Credits for THIS semester
        model.addAttribute("totalCredits", totalCredits);       // Total credits earned historically
        model.addAttribute("searchQuery", searchQuery);
    }
}