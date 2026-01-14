package com.got.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBManager {

    // 修改连接字符串：去掉 integratedSecurity，加上 user 和 password
    private static final String DB_URL = 
        "jdbc:sqlserver://localhost:1433;databaseName=GOT_DB;encrypt=true;trustServerCertificate=true;";

    private static final String DB_USER = "got_user"; // 刚才创建的用户名
    private static final String DB_PASS = "123456";   // 刚才创建的密码

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setUrl(DB_URL);
                dataSource.setUsername(DB_USER); // 设置用户名
                dataSource.setPassword(DB_PASS); // 设置密码

                jdbcTemplate = new JdbcTemplate(dataSource);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Database Driver not found!");
            }
        }
        return jdbcTemplate;
    }
}
