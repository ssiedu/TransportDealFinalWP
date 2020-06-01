package com.wp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//replacemet of web.xml
public class MyDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {//which class to considered as configuration class 
		// TODO Auto-generated method stub
		return new Class[] {ProjectConfig.class};
	}

	@Override
	protected String[] getServletMappings() {//	tell which path to be redirected to dispatcher servlet	
		return new String[] {"/"};
	}

}
