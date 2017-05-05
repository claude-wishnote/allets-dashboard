package com.allets.backend.dashboard.server.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

/**
 * Json 전용 뷰 리졸버.
 */
public class JsonViewResolver implements ViewResolver, Ordered {

	
	private int order = Integer.MAX_VALUE; // default: same as non-Ordered

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang
	 * .String, java.util.Locale)
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale)
	        throws Exception {

		System.out.println("############" + viewName);
		MappingJackson2JsonView view = new MappingJackson2JsonView();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.ALWAYS);

		DefaultSerializerProvider provider = new DefaultSerializerProvider.Impl();
		provider.setNullValueSerializer(new NullSerializer());
		objectMapper.setSerializerProvider(provider);

		view.setObjectMapper(objectMapper);

		return view;
	}

}
