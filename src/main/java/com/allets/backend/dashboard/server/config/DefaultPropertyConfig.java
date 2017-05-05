package com.allets.backend.dashboard.server.config;

import com.allets.backend.dashboard.server.utils.ConfigPropertyPlaceholderConfigurer;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * property config.
 * -Dspring.profiles.active=local
 */
@Configuration
public class DefaultPropertyConfig {

	final static Logger log = LoggerFactory
			.getLogger(DefaultPropertyConfig.class);
	
	final static String ACTIVE_PROFILE_PROPERTY_NAME = "spring.profiles.active";

	@Bean
	public static PropertyPlaceholderConfigurer properties() throws Exception {
		PropertyPlaceholderConfigurer ppc = new ConfigPropertyPlaceholderConfigurer();
		ppc.setIgnoreUnresolvablePlaceholders(true);
		String deploy = System.getProperty(ACTIVE_PROFILE_PROPERTY_NAME);
		
		
		Resource[] prodResources = new PathMatchingResourcePatternResolver()
				.getResources("classpath:/META-INF/profile/prod.properties");
		
		Resource[] overrideResources = new PathMatchingResourcePatternResolver()
				.getResources("classpath:/META-INF/profile/"+ deploy +".properties");

		Resource[] allResources = ArrayUtils.addAll(prodResources);
		allResources = ArrayUtils.addAll(allResources, overrideResources);

		ppc.setLocations(allResources);

		return ppc;
	}

}
