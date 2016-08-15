package com.enterprise.adapter.spring.root;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * 
 * @author anuj.kumar2
 *
 */
@Configuration
@EnableTransactionManagement
@ImportResource({ "classpath:com/enterprise/adapter/spring/root/application-context.xml" })
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