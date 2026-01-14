package com.got.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.got.dao.CourseDAO;
import com.got.dao.StudentDAO;
import com.got.model.AcademicCourse;
import com.got.model.Student;

@WebServlet("/CourseSelection")
public class CourseSelectionServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();
    private CourseDAO courseDAO = new CourseDAO();
    
    // Define the current academic semester (Hardcoded for this session)
    private static final String CURRENT_SEMESTER = "2025/2026 I";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String action = request.getParameter("action"); 
        
        request.setAttribute("activePage", "CourseSelection");

        // 1. Initial Check: Is a student ID provided?
        if (studentId == null) {
            // If no ID is present, fetch the full list of students from the database
            List<Student> list = studentDAO.getAllStudents();
            
            // Set attributes to display the student selection list
            request.setAttribute("studentList", list);
            request.setAttribute("targetUrl", "CourseSelection"); 
            
            // Forward the request to the main selection page
            request.getRequestDispatcher("CourseSelection.jsp").forward(request, response);
            return;
        }

        // Fetch the specific student details using the ID
        Student student = studentDAO.getStudentByMatrix(studentId);

        // 2. Action Handling: Process specific user requests based on the 'action' parameter
        if ("add".equals(action)) {
            // Action: 'add' - Register a new course for the student
            String codeToAdd = request.getParameter("code");
            if (codeToAdd != null) {
                // Insert the course into the database for the current semester
                courseDAO.addCourseForStudent(studentId, codeToAdd, CURRENT_SEMESTER);
            }
        } else if ("remove".equals(action)) {
            // Action: 'remove' - Delete a course from the student's selection
            String codeToRemove = request.getParameter("code");
            if (codeToRemove != null) {
                // Remove the record from the database
                courseDAO.removeCourseForStudent(studentId, codeToRemove);
            }
        } else if ("search".equals(action)) {
            // Action: 'search' - Find courses available in the catalog
            String query = request.getParameter("query");
            if (query != null && !query.trim().isEmpty()) {
                // Perform a search in the database and store results
                List<AcademicCourse> searchResults = courseDAO.searchCourses(query);
                request.setAttribute("searchResults", searchResults);
                request.setAttribute("searchQuery", query);
            }
        }

        // 3. Data Retrieval: Fetch the updated list of courses for this student
        // This list is used to display the "Selected Courses" table
        // Note: Currently, this fetches all courses associated with the student.
        List<AcademicCourse> currentSelected = courseDAO.getStudentCourses(studentId);

        // Calculate the total number of credits based on the currently selected courses
        int selectedCredits = currentSelected.stream().mapToInt(AcademicCourse::getCredit).sum();
        int totalCredits = selectedCredits; // Simplified logic: Total credits equal selected credits

        // 4. Render View: Prepare data and forward to the detail page
        request.setAttribute("student", student);
        request.setAttribute("selectedCourses", currentSelected);
        request.setAttribute("selectedCredits", selectedCredits);
        request.setAttribute("totalCredits", totalCredits); 
        
        request.getRequestDispatcher("CourseSelectionDetail.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle Submit Button logic
        // Since database updates (add/remove) happen instantly in doGet, 
        // this method primarily serves as a confirmation step.
        
        request.setAttribute("message", "Course selection confirmed!");
        
        // Reuse the doGet logic to refresh the page with the success message
        doGet(request, response);
    }
}
