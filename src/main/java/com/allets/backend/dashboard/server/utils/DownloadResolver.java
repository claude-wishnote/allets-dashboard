package com.allets.backend.dashboard.server.utils;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Json 전용 뷰 리졸버.
 */
public class DownloadResolver implements ViewResolver, Ordered {

	
	private int order = Integer.MAX_VALUE; // default: same as non-Ordered

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang
	 * .String, java.util.Locale)
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale)
	        throws Exception {

		DownloadView view = new DownloadView();

		return view;
	}

}
