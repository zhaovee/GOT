package com.got.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.ApplicationDAO;
import com.got.dao.CourseDAO;
import com.got.dao.StudentDAO;
import com.got.model.AcademicCourse;
import com.got.model.AcademicSemester;
import com.got.model.Student;

@WebServlet("/AcademicProgress")
public class AcademicProgressServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        request.setAttribute("activePage", "AcademicProgress");

        if (studentId == null) {
            // No student ID provided: Fetch and display the list of all students
            List<Student> list = studentDAO.getAllStudents();
            request.setAttribute("studentList", list);
            request.setAttribute("targetUrl", "AcademicProgress");
            request.getRequestDispatcher("AcademicProgress.jsp").forward(request, response);
        } else {
            // Student ID provided: Prepare and display specific student details
            prepareDetailData(request, studentId);
            request.getRequestDispatcher("AcademicProgressDetail.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId"); 
        
        // 1. Process Evaluation Note
        // If an evaluation message exists, save it as a notification
        String evaluation = request.getParameter("evaluation");
        if (evaluation != null && !evaluation.trim().isEmpty()) {
            applicationDAO.sendNotification(studentId, evaluation);
        }

        // 2. Process Course Status Updates
        // Rebuild the semester structure to match the index-based parameters from the JSP form
        List<AcademicSemester> semesters = buildSemesterStructure(studentId);
        
        for (int i = 0; i < semesters.size(); i++) {
            List<AcademicCourse> courses = semesters.get(i).getCourses();
            for (int j = 0; j < courses.size(); j++) {
                String paramName = "status_" + i + "_" + j;
                String newStatus = request.getParameter(paramName);
                
                // Update database only if the status has changed
                AcademicCourse c = courses.get(j);
                if (newStatus != null && !newStatus.equals(c.getStatus())) {
                    courseDAO.updateCourseStatus(studentId, c.getCode(), newStatus);
                }
            }
        }

        // 3. Reload Data and Render View
        request.setAttribute("activePage", "AcademicProgress");
        request.setAttribute("message", "Data updated successfully!");
        prepareDetailData(request, studentId);
        request.getRequestDispatcher("AcademicProgressDetail.jsp").forward(request, response);
    }

    // Prepare all necessary data for the student detail view
    private void prepareDetailData(HttpServletRequest request, String studentId) {
        Student student = studentDAO.getStudentByMatrix(studentId);
        
        // Organize courses by semester
        List<AcademicSemester> semesters = buildSemesterStructure(studentId);
        
        // Get the latest evaluation note
        String savedEvaluation = applicationDAO.getLatestNotification(studentId);

        // Calculate total credits and current semester credits based on 'PASS' status
        int totalCredits = 0;
        int currentSemCredits = 0;
        String currentSemesterName = "2024/2025 II"; 

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

        request.setAttribute("targetStudent", student);
        request.setAttribute("semesters", semesters);
        request.setAttribute("totalCredits", totalCredits);
        request.setAttribute("currentSemCredits", currentSemCredits);
        request.setAttribute("savedEvaluation", savedEvaluation);
    }
    
    // Helper method to fetch student courses and group them into semesters
    private List<AcademicSemester> buildSemesterStructure(String studentId) {
        List<AcademicCourse> rawCourses = courseDAO.getStudentCourses(studentId);
        
        // Use LinkedHashMap to maintain semester order
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
