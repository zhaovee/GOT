package com.got.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GPACalculatorController {

    // Helper class to store calculation results for view rendering
    // Matches the property names expected by JSP EL (e.g., ${res.resultGpa})
    public static class CourseResult {
        int id;             // Course sequence number (1, 2, 3...)
        int credit;         // Credit hours input by user
        String resultGpa;   // Calculated required GPA (formatted String)

        public CourseResult(int id, int credit, String resultGpa) {
            this.id = id;
            this.credit = credit;
            this.resultGpa = resultGpa;
        }

        // Getters are required for JSP EL access
        public int getId() { return id; }
        public int getCredit() { return credit; }
        public String getResultGpa() { return resultGpa; }
    }

    // Handle GET request to show the calculator page
    @GetMapping("/GPACalculator")
    public String showCalculator(Model model) {
        model.addAttribute("activePage", "GPACalculator");
        return "GPACalculator";
    }

    // Handle POST request to calculate required grades based on Target GPA
    @PostMapping("/GPACalculator")
    public String calculateRequiredGPA(
            @RequestParam("targetGPA") double targetGPA,
            HttpServletRequest request, 
            Model model) {

        // 1. Retrieve all credit inputs (JSP uses name="credits" for all dynamic inputs)
        String[] creditParams = request.getParameterValues("credits");
        List<CourseResult> results = new ArrayList<>();
        
        if (creditParams != null) {
            List<InternalCourse> courses = new ArrayList<>();
            int totalCredits = 0;

            // 2. Parse inputs and build internal list
            for (int i = 0; i < creditParams.length; i++) {
                try {
                    int credit = Integer.parseInt(creditParams[i]);
                    // id is i+1 for display purposes
                    courses.add(new InternalCourse(i + 1, credit));
                    totalCredits += credit;
                } catch (NumberFormatException e) {
                    // Ignore invalid inputs, treat as 0 or skip
                }
            }

            // 3. Algorithm: "Maximize score on low credits, minimize on high credits"
            
            // Step 3a: Calculate Total Points required to meet Target GPA
            // Formula: TargetGPA * TotalCredits = RequiredTotalPoints
            double requiredTotalPoints = targetGPA * totalCredits;
            
            // Step 3b: Calculate Maximum Possible Points (assuming 4.0 for all courses)
            double maxPossiblePoints = 4.0 * totalCredits;

            if (requiredTotalPoints > maxPossiblePoints) {
                // If required points exceed theoretical max, return an error
                model.addAttribute("error", "Target GPA is too high! It is impossible to achieve even with all 4.0s.");
            } else {
                // Step 3c: Calculate "Surplus" points (The buffer we have to lower grades)
                // We start conceptually with everyone at 4.0, and subtract points from the "Hardest" courses.
                double surplusPoints = maxPossiblePoints - requiredTotalPoints;

                // Sort courses by Credit DESCENDING (Heaviest/Hardest first)
                // Strategy: Use the surplus to lower the grade of high-credit courses first.
                courses.sort(Comparator.comparingInt(InternalCourse::getCredit).reversed());

                for (InternalCourse c : courses) {
                    // Max points we can remove from this course (reducing 4.0 down to 0.0)
                    double maxReducibleForThisCourse = 4.0 * c.credit;

                    if (surplusPoints >= maxReducibleForThisCourse) {
                        // We can reduce this course all the way to 0.0 and still have surplus
                        c.assignedPoint = 0.0; 
                        surplusPoints -= maxReducibleForThisCourse;
                    } else {
                        // We can only reduce partially
                        // Reduction in Grade Point = Surplus / Credit
                        double reduction = surplusPoints / c.credit;
                        c.assignedPoint = 4.0 - reduction;
                        surplusPoints = 0; // Surplus used up
                    }
                }

                // 4. Prepare results for View (Restore original order by ID for consistent display)
                courses.sort(Comparator.comparingInt(InternalCourse::getId));

                for (InternalCourse c : courses) {
                    // Format to 2 decimal places for display
                    String formattedGpa = String.format("%.2f", c.assignedPoint);
                    results.add(new CourseResult(c.id, c.credit, formattedGpa));
                }

                model.addAttribute("results", results);
                model.addAttribute("calculatedTarget", targetGPA);
                
                // Pass back the original credits to keep the form filled after submission
                model.addAttribute("originalCredits", creditParams);
                // Also need to pass courseCount so the dropdown stays correct
                model.addAttribute("courseCount", creditParams.length);
            }
        }

        model.addAttribute("activePage", "GPACalculator");
        return "GPACalculator";
    }

    // Internal helper class for calculation logic
    private static class InternalCourse {
        int id;
        int credit;
        double assignedPoint; 

        public InternalCourse(int id, int credit) {
            this.id = id;
            this.credit = credit;
            this.assignedPoint = 4.0; // Default max score logic base
        }

        public int getId() { return id; }
        public int getCredit() { return credit; }
    }
}