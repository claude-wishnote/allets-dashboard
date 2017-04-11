package com.allets.backend.data.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Properties 파일 정보를 얻는다.
 * <p/>
 * <p/>
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 4
 */
public class ConfigUtil {

	private static final Logger log = LoggerFactory
            .getLogger(ConfigBuildFactoryBean.class);
	
    @Autowired
    ConfigPropertyPlaceholderConfigurer configurer;

    /* key값에 해당하는 properties 값을 리턴 한다. */
    public String getProperty(String key) {

        if (configurer == null) {
            log.warn("ConfigPropertyPlaceholderConfigurer bean is null!!!1");
            return "";
        }
        return configurer.getProperty(key);
    }

}
