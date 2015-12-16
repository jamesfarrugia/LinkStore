package com.jf.java.web.ls;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class for the spring web app.
 * 
 * @author jfarrugia
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.jf.java.web.ls" })
public class SpringWebConfig extends WebMvcConfigurerAdapter 
{
}