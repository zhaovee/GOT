# GOT - PROJECT
# Project Overview
  GOT Monitoring System is a Java Web–based student academic monitoring system developed using Servlet + JSP + JDBC following the MVC architecture.
  The system is designed to manage students, courses, and academic performance, and to support basic monitoring and reporting functionalities.

# Technology Stack
Backend: Java, Spring / Spring MVC
Frontend: JSP, HTML, CSS, JavaScript
Database: Microsoft SQL Server
ORM / Persistence: JPA / Hibernate
Server: Apache Tomcat 9.x
IDE: Spring Tool Suite (STS) 
Build Tool: Maven

# Setup & Installation
  1.Clone or Import Project
  2.Database Configuration
    2.1 Create a database in SQL Server:  CREATE DATABASE GOT_DB;
    2.2 Configuration Database in "java\com\got\config\Appconfig.java"
          dataSource.setUrl("jdbc:sqlserver://localhost:“Appropriate port”;databaseName=GOT_DB;encrypt=true;trustServerCertificate=true;");
          dataSource.setUsername("your username");
          dataSource.setPassword("your password");
  3.Run on Tomcat

# Default Environment
  JDK: 17
  Tomcat: 9.0
  Database: SQL Server

# Author
  Liu Ruoyan, Zhao Wei, Bu Guoshun, Liu Wanpeng
