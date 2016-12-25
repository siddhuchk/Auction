package com.aks.spring.root;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

	@Autowired
	private Environment environment;

	@Bean(name = "exception")
	public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties exceptionMappings = new Properties();
		exceptionMappings.put("Exception", "Exception");
		exceptionResolver.setExceptionMappings(exceptionMappings);
		return exceptionResolver;
	}

	@Bean(name = "blackListDomain")
	public Map<String, String> getBlackListDomain() {
		String domainsList[] = environment.getProperty("blacklist.domain").split(",");
		Map<String, String> blackListedDomains = new HashMap<String, String>();
		for (String s : domainsList) {
			blackListedDomains.put(s, s);
		}
		return blackListedDomains;
	}
	
	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
		t.setCorePoolSize(10);
		t.setMaxPoolSize(200);
		t.setQueueCapacity(500);
		t.setWaitForTasksToCompleteOnShutdown(true);

		return t;
	}
}