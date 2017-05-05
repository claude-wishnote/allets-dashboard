package com.allets.backend.dashboard.server.utils;


/**
 * DB 관련 유틸리티 서비스
 * 
 * <p>
 * .
 *
 */
public class DBUtil {

	/**
	 * 표준 정의된 mybatis statment를 생성 한다.
	 *
	 * @param clazz the clazz
	 * @param sqlId the sql id
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String statement(Class<?> clazz, String sqlId) throws Exception {
		return clazz.getName() + "." + sqlId;
	}

}
