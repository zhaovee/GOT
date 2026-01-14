<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>GPA Calculator - GOT</title>
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .input-group { margin-bottom: 20px; }
        .input-label { display: block; font-size: 13px; color: #555; margin-bottom: 8px; font-weight: 600; }
        .input-field, .select-field {
            width: 100%; padding: 12px; border-radius: 8px; border: none;
            background-color: #F0F0F0; font-size: 14px; box-sizing: border-box;
            transition: 0.2s;
        }
        .input-field:focus, .select-field:focus {
            background-color: #E0E0E0; outline: 2px solid #ddd;
        }
        
        .grid-inputs {
            display: grid; grid-template-columns: repeat(3, 1fr); gap: 30px; margin-top: 30px;
        }
        
        .btn-calc {
            background-color: #111; color: white; padding: 12px 60px; border-radius: 8px;
            border: none; font-size: 14px; cursor: pointer; display: block; margin: 40px auto 0 auto;
            transition: 0.2s;
        }
        .btn-calc:hover { opacity: 0.9; transform: translateY(-1px); }

        .result-section { text-align: center; margin-top: 50px; border-top: 1px solid #eee; padding-top: 30px;}
        .big-gpa {
            font-size: 56px; font-weight: bold; color: #333;
            display: inline-block; margin-top: 10px; margin-bottom: 30px;
        }
    </style>
</head>
<body>

    <jsp:include page="includes/sidebar.jsp" />

    <div class="main-content">
        <c:set var="pageTitle" value="GPA Calculator" scope="request"/>
        <c:set var="breadcrumb" value="Tools" scope="request"/>
        <jsp:include page="includes/header.jsp" />

        <div class="card" style="min-height: 600px;">
            
            <form action="GPACalculator" method="post" id="gpaForm">
                <div style="display: flex; justify-content: space-between; gap: 50px; margin-bottom: 20px;">
                    <div style="flex: 1;">
                        <label class="input-label">Input Target GPA</label>
                        <input type="number" step="0.01" min="0" max="4.0" name="targetGPA" 
                               value="${calculatedTarget}" class="input-field" placeholder="e.g. 3.50" required>
                    </div>

                    <div style="flex: 1;">
                        <label class="input-label">Choose how many courses to take</label>
                        <select id="courseCountSelect" name="count" class="select-field" onchange="generateInputs()">
                            <option value="0">Select count...</option>
                            <c:forEach begin="1" end="10" var="i">
                                <option value="${i}" ${courseCount == i ? 'selected' : ''}>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div id="courseInputsContainer" class="grid-inputs">
                    <c:if test="${not empty originalCredits}">
                        <c:forEach var="cred" items="${originalCredits}" varStatus="status">
                            <div class="input-group">
                                <label class="input-label">Course ${status.count} Credit</label>
                                <input type="number" name="credits" value="${cred}" class="input-field" required>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>

                <button type="submit" class="btn-calc">Calculate</button>
            </form>

            <c:if test="${not empty results}">
                <div class="result-section">
                    <h3 style="color: #666; font-weight: normal; margin: 0;">Your Target CGPA</h3>
                    <div class="big-gpa">${calculatedTarget}</div>

                    <div class="grid-inputs">
                        <c:forEach var="res" items="${results}">
                            <div class="input-group">
                                <label class="input-label">Target GPA for Course ${res.id} (${res.credit} Credits)</label>
                                <!-- 显示结果为只读，加深颜色 -->
                                <div class="input-field" style="background-color: #e8e8e8; font-weight: bold; color: #333;">${res.resultGpa}</div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <p style="color: #d32f2f; text-align: center; margin-top: 20px; font-weight: bold;">${error}</p>
            </c:if>
        </div>
    </div>

    <script>
        function generateInputs() {
            var count = document.getElementById("courseCountSelect").value;
            var container = document.getElementById("courseInputsContainer");
            
            // 简单的交互优化：只有数量变化才重绘
            var currentInputs = container.getElementsByTagName("input").length;
            
            if (currentInputs != count) {
                container.innerHTML = ""; // Clear
                for (var i = 1; i <= count; i++) {
                    var div = document.createElement("div");
                    div.className = "input-group";
                    
                    var label = document.createElement("label");
                    label.className = "input-label";
                    label.innerText = "Course " + i + " Credit";
                    
                    var input = document.createElement("input");
                    input.type = "number";
                    input.name = "credits";
                    input.className = "input-field";
                    input.required = true;
                    input.placeholder = "Credit hours";
                    
                    div.appendChild(label);
                    div.appendChild(input);
                    container.appendChild(div);
                }
            }
        }
    </script>
</body>
</html>