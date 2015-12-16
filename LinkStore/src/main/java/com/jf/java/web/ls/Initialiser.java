package com.jf.java.web.ls;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Implementation of the {@link WebApplicationInitializer}.  Defines the 
 * dispatcher servlet at the /api/ endpoint.
 * 
 * @author jfarrugia
 *
 */
public class Initialiser implements WebApplicationInitializer 
{
	public void onStartup(ServletContext servletContext)
	throws ServletException 
	{
		AnnotationConfigWebApplicationContext ctx = 
				new AnnotationConfigWebApplicationContext();
		
		ctx.register(SpringWebConfig.class);
		ctx.setServletContext(servletContext);
		
		DispatcherServlet servlet = new DispatcherServlet(ctx);
		Dynamic dynamic = servletContext.addServlet("dispatcher", servlet);
		dynamic.addMapping("/api/*");
		dynamic.setLoadOnStartup(1);
	}
}