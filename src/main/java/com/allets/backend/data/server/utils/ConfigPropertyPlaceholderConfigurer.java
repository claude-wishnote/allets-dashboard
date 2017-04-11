package com.allets.backend.data.server.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jackie on 2015-04-10.
 */
public class ConfigPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	  Logger log = LoggerFactory.getLogger(this.getClass());
	
    /* context 와 servlet context 각각 로딩 되기 때문에 체크를 위한 상수 */
    public static boolean first = true;

    Map propertiesMap;

    /* 실제 properties를 처리한다. 기본적으로 부모 메서드를 실행 하고 서버 로딩시 로그를 출력하고, 클래스에서 getProperty로 값을 읽도록 처리 */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        propertiesMap = Maps.newHashMap();
        StringBuffer message = new StringBuffer();

        String title = "System Properties Information";
        
        message.append("\n\n######################################################\n")
        .append("# " + title + " \n")
        .append("######################################################\n");        
        
        log.info(message.toString());
        
        Properties p = System.getProperties();
        Enumeration<Object> keys = p.keys();
        while (keys.hasMoreElements()) {
          String key = (String)keys.nextElement();
          String value = (String)p.get(key);
          log.info(key + "=" + value);
        }

        message.setLength(0);
        title = "Profile Properties Information";
        //context 로딩이면
        if (!first) {
            title = "Profile Properties Information";
        }
        message.append("\n\n######################################################\n")
                .append("# " + title + " \n")
                .append("######################################################\n");
        
        log.info(message.toString());
        
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = (String)props.getProperty(keyStr);
            propertiesMap.put(keyStr, value);
            log.info(keyStr + "=" + value);
        }

       
        
        first = false;
    }

    /* properties 값을 리턴 한다 */
    public String getProperty (String key) {
        return (String)propertiesMap.get(key);
    }

}
