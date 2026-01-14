package com.got.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GPACalculator")
public class GPACalculatorServlet extends HttpServlet {
    
    // Inner class to maintain course index and store calculation results
    public static class CourseResult {
        int id;             // Original sequence of the course (Course 1, Course 2...)
        int credit;         // Credit hours for the course
        double resultGpa;   // Calculated recommended GPA for this course

        public CourseResult(int id, int credit) {
            this.id = id;
            this.credit = credit;
            this.resultGpa = 2.0; // Default minimum GPA starts at 2.0
        }
        
        public int getCredit() { return credit; }
        public int getId() { return id; }
        public double getResultGpa() { return resultGpa; }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("activePage", "GPACalculator");
        request.getRequestDispatcher("GPACalculator.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Retrieve Input Parameters
            String targetGpaStr = request.getParameter("targetGPA");
            String[] creditParams = request.getParameterValues("credits"); // Retrieve credit values for all courses
            
            // Validate that inputs are not missing
            if (targetGpaStr == null || creditParams == null) {
                throw new Exception("Invalid Input");
            }

            double targetGPA = Double.parseDouble(targetGpaStr);
            List<CourseResult> courses = new ArrayList<>();
            int totalCredits = 0;

            // 2. Encapsulate Data
            // Parse credit inputs and create CourseResult objects
            for (int i = 0; i < creditParams.length; i++) {
                if (!creditParams[i].isEmpty()) {
                    int credit = Integer.parseInt(creditParams[i]);
                    courses.add(new CourseResult(i + 1, credit));
                    totalCredits += credit;
                }
            }

            // 3. Core Algorithm Logic
            // Calculate total grade points required to achieve the target GPA
            double totalPointsNeeded = targetGPA * totalCredits;
            
            // Calculate points assuming everyone starts with a base GPA of 2.0
            double currentPoints = totalCredits * 2.0; 
            
            // Calculate the deficit (points still needed to reach the target)
            double pointsDeficit = totalPointsNeeded - currentPoints;

            if (pointsDeficit <= 0) {
                // If the target is low enough, a 2.0 GPA for all courses is sufficient (or remains at 2.0)
                // Do nothing, everyone stays at 2.0
            } else {
                // Strategy: Minimize the GPA required for high-credit courses.
                // To do this, we prioritize adding points to "low-credit" courses first.
                // Sort by credits in ascending order (smallest credits first)
                courses.sort(Comparator.comparingInt(CourseResult::getCredit));

                for (CourseResult c : courses) {
                    // Stop if we have covered the deficit
                    if (pointsDeficit <= 0) break;

                    // Calculate the maximum points we can add to this course (Targeting max 4.0)
                    double maxAdd = 4.0 - 2.0; // Each course can be increased by at most 2.0 points
                    double pointsCanAbsorb = c.credit * maxAdd; // How many grade points are needed to max out this course

                    if (pointsDeficit >= pointsCanAbsorb) {
                        // If the deficit is large, maximize this course's GPA to 4.0 completely
                        c.resultGpa = 4.0;
                        pointsDeficit -= pointsCanAbsorb;
                    } else {
                        // If the deficit is small, only increase this course's GPA partially to meet the target
                        double gpaIncrease = pointsDeficit / c.credit;
                        c.resultGpa = 2.0 + gpaIncrease;
                        pointsDeficit = 0;
                    }
                }
            }

            // 4. Sort back to original ID for display (Course 1, Course 2...)
            courses.sort(Comparator.comparingInt(CourseResult::getId));

            // Format decimal places (Round to 2 decimal places)
            for(CourseResult c : courses) {
                c.resultGpa = Math.round(c.resultGpa * 100.0) / 100.0;
                // Prevent precision overflow from causing display values greater than 4.0
                if(c.resultGpa > 4.0) c.resultGpa = 4.0; 
            }

            // 5. Set attributes and return response
            request.setAttribute("results", courses);
            request.setAttribute("calculatedTarget", targetGPA);
            request.setAttribute("originalCredits", creditParams); // Used to repopulate the form
            request.setAttribute("courseCount", creditParams.length); // Used to re-select the dropdown value
            
        } catch (Exception e) {
            request.setAttribute("error", "Please check your inputs.");
        }
        
        request.setAttribute("activePage", "GPACalculator");
        request.getRequestDispatcher("GPACalculator.jsp").forward(request, response);
    }
}
