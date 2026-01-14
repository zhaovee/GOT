package com.got.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.got.dao.ApplicationDAO;
import com.got.dao.CourseDAO;
import com.got.dao.StudentDAO;
import com.got.model.AcademicCourse;
import com.got.model.AcademicSemester;
import com.got.model.Student;

@Controller
public class AcademicProgressController {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private ApplicationDAO applicationDAO;

    // Handle GET Request: View List or View Detail
    @GetMapping("/AcademicProgress")
    public String viewProgress(@RequestParam(value = "studentId", required = false) String studentId, Model model) {
        
        model.addAttribute("activePage", "AcademicProgress");

        if (studentId == null) {
            // Scenario 1: No Student ID provided -> Show Student List
            List<Student> list = studentDAO.getAllStudents();
            model.addAttribute("studentList", list);
            
            // This attribute tells the JSP which URL to post/link to next
            model.addAttribute("targetUrl", "AcademicProgress");
            
            return "AcademicProgress"; // Resolves to AcademicProgress.jsp
        } else {
            // Scenario 2: Student ID provided -> Show Progress Details
            prepareDetailData(studentId, model);
            return "AcademicProgressDetail"; // Resolves to AcademicProgressDetail.jsp
        }
    }

    // Handle POST Request: Update Grades/Status and Send Evaluation
    @PostMapping("/AcademicProgress")
    public String updateProgress(
            @RequestParam("studentId") String studentId,
            @RequestParam(value = "evaluation", required = false) String evaluation,
            HttpServletRequest request, // Needed for dynamic parameter names (status_0_1, etc.)
            Model model) {
        
        // 1. Process Evaluation Note (if any)
        if (evaluation != null && !evaluation.trim().isEmpty()) {
            applicationDAO.sendNotification(studentId, evaluation);
        }

        // 2. Process Course Status Updates
        // We need to rebuild the structure to know how many courses to iterate over
        // This mirrors the logic in the original Servlet to match JSP form indexes
        List<AcademicSemester> semesters = buildSemesterStructure(studentId);
        
        for (int i = 0; i < semesters.size(); i++) {
            List<AcademicCourse> courses = semesters.get(i).getCourses();
            for (int j = 0; j < courses.size(); j++) {
                // Construct the parameter name used in the JSP: status_{semesterIndex}_{courseIndex}
                String paramName = "status_" + i + "_" + j;
                String newStatus = request.getParameter(paramName);
                
                AcademicCourse c = courses.get(j);
                // Only update database if status has changed
                if (newStatus != null && !newStatus.equals(c.getStatus())) {
                    courseDAO.updateCourseStatus(studentId, c.getCode(), newStatus);
                }
            }
        }

        // 3. Reload Data and Show Success Message
        model.addAttribute("activePage", "AcademicProgress");
        model.addAttribute("message", "Data updated successfully!");
        
        prepareDetailData(studentId, model);
        return "AcademicProgressDetail";
    }

    // Helper method to prepare data for the Detail View
    private void prepareDetailData(String studentId, Model model) {
        Student student = studentDAO.getStudentByMatrix(studentId);
        List<AcademicSemester> semesters = buildSemesterStructure(studentId);
        String savedEvaluation = applicationDAO.getLatestNotification(studentId);

        // Calculate credits
        int totalCredits = 0;
        int currentSemCredits = 0;
        String currentSemesterName = "2024/2025 II"; // Example constant, ideally from config

        for (AcademicSemester sem : semesters) {
            for (AcademicCourse course : sem.getCourses()) {
                if ("PASS".equals(course.getStatus())) {
                    totalCredits += course.getCredit();
                    if (sem.getName().equals(currentSemesterName)) {
                        currentSemCredits += course.getCredit();
                    }
                }
            }
        }

        model.addAttribute("targetStudent", student);
        model.addAttribute("semesters", semesters);
        model.addAttribute("totalCredits", totalCredits);
        model.addAttribute("currentSemCredits", currentSemCredits);
        model.addAttribute("savedEvaluation", savedEvaluation);
    }
    
    // Helper method to organize courses into Semesters
    private List<AcademicSemester> buildSemesterStructure(String studentId) {
        List<AcademicCourse> rawCourses = courseDAO.getStudentCourses(studentId);
        Map<String, List<AcademicCourse>> grouped = new LinkedHashMap<>();
        
        for (AcademicCourse c : rawCourses) {
            String sem = c.getSemester();
            if (sem == null) sem = "Unassigned";
            grouped.computeIfAbsent(sem, k -> new ArrayList<>()).add(c);
        }
        
        List<AcademicSemester> result = new ArrayList<>();
        for (Map.Entry<String, List<AcademicCourse>> entry : grouped.entrySet()) {
            result.add(new AcademicSemester(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}
