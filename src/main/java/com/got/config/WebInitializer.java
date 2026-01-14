package com.got.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Load the configuration class
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // Load the web application context configuration
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    // Map the DispatcherServlet to the root path
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}