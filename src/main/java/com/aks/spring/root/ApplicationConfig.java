package com.aks.spring.root;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * 
 * @author anuj.siddhu
 *
 */
@Configuration
@EnableTransactionManagement
@ImportResource({ "classpath:com/aks/spring/root/application-context.xml" })
public class ApplicationConfig {

	@Bean(name = "exception")
	public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties exceptionMappings = new Properties();
		exceptionMappings.put("Exception", "Exception");
		exceptionResolver.setExceptionMappings(exceptionMappings);
		return exceptionResolver;
	}
}