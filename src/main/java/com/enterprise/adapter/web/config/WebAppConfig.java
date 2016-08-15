package com.enterprise.adapter.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Configuration("webAppConfig")
public class WebAppConfig extends WebMvcConfigurationSupport {

	public WebAppConfig() {
		super();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getJsonFactory().enable(
				JsonGenerator.Feature.ESCAPE_NON_ASCII);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		AnnotationIntrospector primary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector secondary = new JaxbAnnotationIntrospector(
				objectMapper.getTypeFactory());
		AnnotationIntrospector pair = new AnnotationIntrospector.Pair(
				secondary, primary);
		objectMapper.setAnnotationIntrospector(pair);
		return objectMapper;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJacksonHttpMessageConverter.setPrettyPrint(true);
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		mappingJacksonHttpMessageConverter
				.setSupportedMediaTypes(supportedMediaTypes);
		mappingJacksonHttpMessageConverter.setObjectMapper(objectMapper());
		return mappingJacksonHttpMessageConverter;
	}

	@Override
	public void configureMessageConverters(
			final List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJacksonHttpMessageConverter());
	}

	@Override
	protected void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Bean
	public ViewResolver viewResolver() {
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(new MappingJackson2JsonView());
		// viewResolver.setViewResolvers(viewResolvers());
		// defaultViews.add(new JstlView());
		viewResolver.setDefaultViews(defaultViews);
		return viewResolver;
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.mediaType("html", MediaType.TEXT_HTML).mediaType("json",
				MediaType.APPLICATION_JSON);
	}

}
