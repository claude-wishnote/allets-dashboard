package com.allets.backend.data.server.utils;

import com.google.common.collect.Lists;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;

/**
 * 리소스 파일에 대한 정보를 조회(classpath, WEB-INF) 하는 유틸리티
 * 
 * <p>
 * 
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 9
 */
public class ResourceUtil {

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

	public static List<String> getResource(String... basenames) throws Exception {

		List<String> paths = Lists.newArrayList();
		
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		
		
		for (String basename : basenames) {
			
			Resource[] resources = resourcePatternResolver.getResources(basename);
			
		}
		
		return null;
	}

	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */

}
