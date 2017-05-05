package com.allets.backend.dashboard.server.utils;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * The Class MessageUtil.
 */
public class MessageUtil {

	/** The ms acc. */
	private MessageSourceAccessor msAcc = null;

	/** 시스템 로케일 정보. */
	private Locale systemLocale = Locale.getDefault();

	/**
	 * Gets the message.
	 *
	 * @param key
	 *            the key
	 * @return the message
	 */
	public String getMessage(String key) {
		HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
 		return msAcc.getMessage(key, LocaleUtil.getCurrentLocale(httpRequest));
	}

	/**
	 * Gets the message with param.
	 *
	 * @param key the key
	 * @param params the params
	 * @return the message with param
	 */
	public String getMessageWithParam(String key, String[] params) {
		HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return msAcc.getMessage(key, params, systemLocale);
	}

	/**
	 * Sets the message source accessor.
	 *
	 * @param msAcc
	 *            the new message source accessor
	 */
	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
		this.msAcc = msAcc;
	}

	/**
	 * Gets the system locale.
	 *
	 * @return the system locale
	 */
	public Locale getSystemLocale() {
		return systemLocale;
	}

	/**
	 * Sets the system locale.
	 *
	 * @param systemLocale
	 *            the new system locale
	 */
	public void setSystemLocale(Locale systemLocale) {
		this.systemLocale = systemLocale;
	}

}
