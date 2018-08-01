/**
 * 
 */
package com.bridgeit.todoapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgeit.todoapplication.utilityservice.interceptor.NoteInterceptor;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>POJO Class having User related information and method.</b>
 *        </p>
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Autowired
	NoteInterceptor noteInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new NoteInterceptor()).addPathPatterns("/note/**");
	}
}
