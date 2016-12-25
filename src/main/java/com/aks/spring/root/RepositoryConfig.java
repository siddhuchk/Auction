package com.aks.spring.root;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author anuj.siddhu
 *
 */
@Configuration
@EnableTransactionManagement
@ImportResource({ "classpath:com/aks/spring/root/repositories-config.xml" })
public class RepositoryConfig {
}