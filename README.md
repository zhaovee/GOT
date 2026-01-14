# GOT - PROJECT
# Project Overview
  GOT Monitoring System is a Java Web–based student academic monitoring system developed using Servlet + JSP + JDBC following the MVC architecture.
  The system is designed to manage students, courses, and academic performance, and to support basic monitoring and reporting functionalities.

# Technology Stack
  Programming Language: Java
  Web Framework: Java Servlet & JSP
  Architecture: MVC (Model–View–Controller)
  Database: SQL Server (configurable via JDBC)
  Server: Apache Tomcat 9
  IDE: Spring Tool Suite (STS)
  Build Tool: Maven

# Setup & Installation
  1.Clone or Import Project
  2.Database Configuration
    Update database connection settings in: com.got.util.DBUtil
    example：
    String url = "jdbc:sqlserver://localhost:1433;databaseName=xxxx";
    String username = "your_name";
    String password = "your_password";
3.Run on Tomcat

# Default Environment
JDK: 17
Tomcat: 9.0
Database: SQL Server

# Author
Liu Ruoyan, Zhao Wei, Bu Guoshun, Liu Wanpeng
