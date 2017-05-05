package com.allets.backend.dashboard.server.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * The Class SystemPropertiesParser.
 */
public class SystemPropertiesParser {

	/** 시스템 환경 변수 lang. */
	final static String LANG_PROPERTY_NAME = "lang";
	
	/** 국가 코드 저장 key 명. */
	final static String COUNTRY_NAME = "country";

	/** deploy 모드 key 명. */
	final static String DEPLOY_NAME = "deploy";
	
	/** 시스템 환경 변수 spring.profiles.active. */
	final static String ACTIVE_PROFILE_PROPERTY_NAME = "spring.profiles.active";
	
	final static String DELEMETER = "-";
	
	static Map<String, String> profileMap = Maps.newHashMap();
	
	/**
	 * 시스템 환경변수 언어셋 정보를 얻는다 (-Dlang=ko_kr).
	 *
	 * @return the lang
	 */
	public static String getLang() {
		return StringUtil.trimToEmpty(System.getProperty(LANG_PROPERTY_NAME));
	}
	
	/**
	 * 국가 정보를 구한다. -Dspring.profiles.active=kor-local 에서 (kor 정보).
	 *
	 * @return the country
	 */
	public static String getCountry() {
		return getProfileMap(COUNTRY_NAME);
	}
	
	/**
	 * 디플로이 환경을 구한다. -Dspring.profiles.active=kor-local 에서 (local 정보).
	 *
	 * @return the country
	 */
	public static String getDeploy() {
		return getProfileMap(DEPLOY_NAME);
	}
	
	/**
	 * 실제로 파싱된 국가 정보 & 배포 정보를 가져 온다.
	 *
	 * @param key the key
	 * @return the profile map
	 */
	private static String getProfileMap (String key) {
		
		//초기화를 하지 않을 경우
		if (profileMap.size() <= 0) {
			
			String profile = System.getProperty(ACTIVE_PROFILE_PROPERTY_NAME);
			String [] profiles = StringUtils.split(profile, DELEMETER);
		
			profileMap.put(COUNTRY_NAME, StringUtils.trimToEmpty(profiles[0]));
			profileMap.put(DEPLOY_NAME, StringUtils.trimToEmpty(profiles[1]));
			
		}
		
		return profileMap.get(key);
	}
	
}
