package com.allets.backend.data.server.utils;

import org.springframework.web.servlet.support.RequestContext;

/**
 * Spring Context를 지원하는 유틸 서비스
 * 
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 2. 25
 */
public class ContextUtil {

	/**
	 * Type에 따라서 현재 스프링 빈을 가져 온다.
	 * 
	 * <p/>
	 * 
	 * ContextService.getBean(SampleComponent.class); <br/>
	 * getContextService.getBean(SampleComponent.class);
	 *
	 * @param <T> the generic type
	 * @param valueType [설명]
	 * @return T [설명]
	 * @throws Exception the exception
	 * @since 2015. 3. 16
	 */
	public static <T> T getBean(RequestContext context  ,final Class<T> valueType)
	        throws Exception {
		return 	context.getWebApplicationContext().getBean(valueType);
	}
}
