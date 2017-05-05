package com.allets.backend.dashboard.server.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.text.SimpleDateFormat;

/**
 * MappingJackson2JsonView 생성 시 ObjectMapper를 injection 한다.
 * 
 * <p>
 * 
 * <ul>
 * <li>[기능1]
 * <li>[기능2]
 * </ul>.
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 25
 */
public class JacksonObjectMapperFactoryBean {

	/*
	 * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * Constructors.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * public & interface method.
	 * ---------------------------------------------------------------------
	 */

	/**
	 * MappingJackson2JsonView에서 사용할 ObjectMapper를 생성 한다 .
	 * 
	 * <p/>
	 * 
	 * [사용 방법 설명].
	 *
	 * @return Object mapper [설명]
	 * @throws Exception
	 *             the exception
	 * @since 2015. 3. 25
	 */
	public static ObjectMapper createObjectMapper() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.ALWAYS);
		
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

		DefaultSerializerProvider provider = new DefaultSerializerProvider.Impl();
		provider.setNullValueSerializer(new NullSerializer());
		objectMapper.setSerializerProvider(provider);

		objectMapper.setDateFormat(new SimpleDateFormat("yyyy MM dd HH:mm:ss"));

		return objectMapper;
	}

	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */

}
