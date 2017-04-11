package com.allets.backend.data.server.utils;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.List;
import java.util.Set;

/**
 * WEB-INF 밑에 리소스를 검색 후 리스트 형태로 리턴하는 팩토리 빈
 *
 * <p>
 *
 */
public class BundleMessageSourceScanner {

	/*
	 * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */

	private static final Logger log = LoggerFactory
			.getLogger(BundleMessageSourceScanner.class);

	/**
	 * The constant INCLUDE_EXTENSION.
     */
/* 메세지 파일 확장자 명 */
	final static String INCLUDE_EXTENSION = ".properties";

	/**
	 * The constant REMOVE_START_CHAR.
     */
/* 삭제할 시작 문자열 */
	final static String REMOVE_START_CHAR = "[";

	/**
	 * The constant REMOVE_END_CHAR.
     */
/* 삭제할 끝 문자열 */
	final static String REMOVE_END_CHAR = "]";

	/**
	 * The constant LAST_INDEX_CHAR.
     */
/* 메세지 타입 검사 문자열 */
	final static String LAST_INDEX_CHAR = "_";

	/**
	 * The constant REMOVE_PATH_CHAR.
     */
	final static String REMOVE_PATH_CHAR = "/";

	/**
	 * The Message root directory.
     */
	static String messageRootDirectory = "/META-INF";

	@Autowired
	private ApplicationContext applicationContext;

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
	 * 지정된 Ant 스타일의 파일패턴을 분석 해서 해당 파일들을 리스트 형태로 리턴 한다. <br/>
	 * getResources는 팩토리빈 생성 메서드.
	 *
	 * <p/>
	 *
	 * getResources(/WEB-INF/messages/**);
	 *
	 *
	 * WEB-INF/messages/Message WEB-INF/messages/order/message_a
	 * WEB-INF/messages/product/message"
	 *
	 *
	 * @param wildcardBase the wildcard base
	 * @return List 모든 리소스 경로
	 * @throws Exception the exception
     * @since 2015. 3. 13
     */
	public List<String> getResources(String wildcardBase) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		Resource[] resources = resolver.getResources(wildcardBase);

		String includeExtension = INCLUDE_EXTENSION;
		Set<String> locationSet = Sets.newHashSet();

		for (Resource resource : resources) {

			String location = resource.getDescription();

			if (StringUtils.contains(location, INCLUDE_EXTENSION)) {

				location = StringUtils.replace(location, "\\", "/");
				int index = StringUtils.indexOf(location, messageRootDirectory);

				if (index > 0) {

					// "_{언어셋}.properties를 삭제 한다."
					location = location.substring(index);
					int subIndex = 0;
					location = StringUtils.substringBefore(location,
							LAST_INDEX_CHAR);
					location = location.substring(1);
					location = location.trim();
					locationSet.add(location);
				}
			}
		}

		StringBuffer message = new StringBuffer();
		message.append(
				"\n\n######################################################\n")
				.append("# Message Resource Information \n")
				.append("######################################################\n");
		log.info(message.toString());
		for (String path : locationSet) {
			log.info(path);
		}

		return CollectionUtil.setToList(locationSet);
	}

	/**
	 * Gets message root directory.
	 *
	 * @return the message root directory
     */
	public static String getMessageRootDirectory() {
		return messageRootDirectory;
	}

	/**
	 * Sets message root directory.
	 *
	 * @param messageRootDirectory the message root directory
     */
	public static void setMessageRootDirectory(String messageRootDirectory) {
		BundleMessageSourceScanner.messageRootDirectory = messageRootDirectory;
	}

	/**
	 * Gets application context.
	 *
	 * @return the application context
     */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Sets application context.
	 *
	 * @param applicationContext the application context
     */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
