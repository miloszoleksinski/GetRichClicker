package com.olas.GetRichClicker.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer 
{
	@Override
	 protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) 
	 {
	        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
	        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	        return dispatcherServlet;
	 }

	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[]{WebConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() 
	{
		return null;
	}

	@Override
	protected String[] getServletMappings() 
	{
		return new String[]{"/"};
	}

}
